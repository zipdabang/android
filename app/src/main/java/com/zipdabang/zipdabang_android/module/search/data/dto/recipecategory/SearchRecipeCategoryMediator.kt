package com.zipdabang.zipdabang_android.module.search.data.dto.recipecategory

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.zipdabang.zipdabang_android.core.Paging3Database
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.core.remotekey.RemoteKeys
import com.zipdabang.zipdabang_android.module.search.data.SearchApi
import com.zipdabang.zipdabang_android.module.search.data.dto.common.SearchRecipe
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import java.lang.Exception
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class SearchRecipeCategoryMediator @Inject constructor(
    private val searchApi: SearchApi,
    private val paging3Database: Paging3Database,
    private val categoryId: Int,
    private val searchText : String,
    private val tokenDataStore: DataStore<Token>
) : RemoteMediator<Int,SearchRecipe>() {

    private val CategoryDao = paging3Database.SearchRecipeDao()
    private val RemoteKeyDao = paging3Database.RemoteKeyDao()
    @OptIn(DelicateCoroutinesApi::class)
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, SearchRecipe>
    ): MediatorResult {
        return try
        {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    Log.e("Refresh","refresh")
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
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


            val accessToken = ("Bearer " + tokenDataStore.data.first().accessToken)

                 val response = searchApi.getSearchRecipeCategory(
                        accessToken = accessToken, pageIndex = currentPage,
                        keyWord = searchText, categoryId = categoryId
                    )
                     if(response.result == null ){
                         CategoryDao.deleteItems()
                         RemoteKeyDao.deleteRemoteKeys()
                         delay(1000)
                         MediatorResult.Success(endOfPaginationReached = true)
                     }


            if(!response.isSuccess){
                        Log.e("Error in SearchCategoryMediator", response.message)
                    }

            val endOfPaginationReached = response.result!!.isLast

            val prevPage = if(currentPage == 1) null else currentPage -1
            val nextPage = if(endOfPaginationReached) null else currentPage + 1

                paging3Database.withTransaction {
                    if(loadType == LoadType.REFRESH){
                        CategoryDao.deleteItems()
                        RemoteKeyDao.deleteRemoteKeys()
                    }
                    val keys = response.result.recipeList.map {  items ->
                        RemoteKeys(
                            id = items.recipeId,
                            prevPage = prevPage,
                            nextPage = nextPage
                        )
                    }
                    RemoteKeyDao.addAllRemoteKeys(remoteKeys = keys)
                    CategoryDao.addItems(items = response.result.recipeList)
                }

                MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        }catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }




    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, SearchRecipe>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.recipeId?.let { id ->
                RemoteKeyDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, SearchRecipe>
    ): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { item ->
                RemoteKeyDao.getRemoteKeys(id = item.recipeId)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int,SearchRecipe>
    ): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { item ->
                RemoteKeyDao.getRemoteKeys(id = item.recipeId)
            }
    }



}