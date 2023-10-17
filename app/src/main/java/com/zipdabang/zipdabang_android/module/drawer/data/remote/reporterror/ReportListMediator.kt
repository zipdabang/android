package com.zipdabang.zipdabang_android.module.drawer.data.remote.reporterror

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.zipdabang.zipdabang_android.common.Constants
import com.zipdabang.zipdabang_android.core.Paging3Database
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.core.remotekey.RemoteKeys
import com.zipdabang.zipdabang_android.module.drawer.data.remote.DrawerApi
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.first
import java.lang.Exception
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ReportListMediator @Inject constructor(
    private val reportApi : DrawerApi,
    private val paging3Database: Paging3Database,
    private val tokenDataStore: DataStore<Token>
) : RemoteMediator<Int,InqueryDB>() {

    private val ReportDao = paging3Database.reportDao()
    private val RemoteKeyDao = paging3Database.RemoteKeyDao()
    val responseMapList = mutableListOf<InqueryDB>()
    @OptIn(DelicateCoroutinesApi::class)
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, InqueryDB>
    ): MediatorResult {
        return try
        {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
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


                 val response = reportApi.getErrorList(
                        accessToken = accessToken, page = currentPage,
                    )
                     if(response.result == null ){
                         ReportDao.deleteItems()
                         RemoteKeyDao.deleteRemoteKeys()

                         MediatorResult.Success(endOfPaginationReached = true)
                     }else{
                         val responseList = response.result.inqueryList
                         Log.e("ReportList Api",responseList.size.toString())
                         responseList.forEachIndexed { index, inqury ->
                             responseMapList.add(InqueryDB(
                                 index = index + (currentPage-1)* Constants.ITEMS_PER_PAGE,
                                 id= inqury.id,
                                 createdAt =  inqury.createdAt,
                                 title = inqury.title
                             )
                             )
                         }
                     }


            if(!response.isSuccess){

                        Log.e("Error in SearchCategoryMediator", response.message)
            }

            val endOfPaginationReached = response.result!!.isLast

            val prevPage = if(currentPage == 1) null else currentPage -1
            val nextPage = if(endOfPaginationReached) null else currentPage + 1

                paging3Database.withTransaction {
                    if(loadType == LoadType.REFRESH){
                        ReportDao.deleteItems()
                        RemoteKeyDao.deleteRemoteKeys()
                    }
                    val keys = responseMapList.map {  items ->
                        RemoteKeys(
                            id = items.id,
                            prevPage = prevPage,
                            nextPage = nextPage
                        )
                    }
                    RemoteKeyDao.addAllRemoteKeys(remoteKeys = keys)
                    ReportDao.addItems(items = responseMapList)

                }

                MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        }catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }




    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, InqueryDB>
    ): RemoteKeys? {
        //state.anchorposition은 현재 화면에 보이는 첫 번째 아이템의 포지션
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.index?.let { id ->
                RemoteKeyDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, InqueryDB>
    ): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { item ->
                RemoteKeyDao.getRemoteKeys(id = item.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, InqueryDB>
    ): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { item ->
                RemoteKeyDao.getRemoteKeys(id = item.id)
            }
    }



}