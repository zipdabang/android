package com.zipdabang.zipdabang_android.module.market.data.marketCategory

import androidx.compose.runtime.MutableState
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.zipdabang.zipdabang_android.core.Paging3Database
import com.zipdabang.zipdabang_android.module.market.data.MarketApi
import java.lang.Exception
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MarketRemoteMediator @Inject constructor(
    private val marketApi: MarketApi,
    private val paging3Database: Paging3Database,
    private val categoryId: Int,
    private val token: String?
) : RemoteMediator<Int,Category_Product>() {

    private val CategoryDao = paging3Database.CategoryDao()
    private val RemoteKeyDao = paging3Database.RemoteKeyDao()


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Category_Product>
    ): MediatorResult {
        return try
        {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    remoteKeys?.nextPage?.minus(1) ?: 0
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }



                val response = marketApi.getCategoryList(
                    token = token, pageIndex = currentPage,
                    categoryId = categoryId)
                val endOfPaginationReached = response.result.isLast

                val prevPage = if(currentPage == 1) null else currentPage
                val nextPage = if(endOfPaginationReached) null else currentPage

                paging3Database.withTransaction {
                    if(loadType == LoadType.REFRESH){
                        CategoryDao.deleteItems()
                        RemoteKeyDao.deleteRemoteKeys()
                    }
                    val keys = response.result.productList.map {  items ->
                        RemoteKeys(
                            id = items.productId,
                            prevPage = prevPage,
                            nextPage = nextPage
                        )
                    }
                    RemoteKeyDao.addAllRemoteKeys(remoteKeys = keys)
                    CategoryDao.addItems(items = response.result.productList)
                }

                MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        }catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }



    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Category_Product>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.productId?.let { id ->
                RemoteKeyDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Category_Product>
    ): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { item ->
                RemoteKeyDao.getRemoteKeys(id = item.productId)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Category_Product>
    ): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { item ->
                RemoteKeyDao.getRemoteKeys(id = item.productId)
            }
    }



}