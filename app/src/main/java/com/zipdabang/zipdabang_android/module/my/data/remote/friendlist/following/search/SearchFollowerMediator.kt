package com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.following.search

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.zipdabang.zipdabang_android.common.Constants
import com.zipdabang.zipdabang_android.common.getErrorCode
import com.zipdabang.zipdabang_android.core.Paging3Database
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.core.remotekey.RemoteKeys
import com.zipdabang.zipdabang_android.module.my.data.remote.MyApi
import com.zipdabang.zipdabang_android.module.search.data.dto.common.SearchRecipe
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.lang.Exception
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class SearchFollowerMediator @Inject constructor(
    private val myApi: MyApi,
    private val paging3Database: Paging3Database,
    private val searchText : String,
    private val tokenDataStore: DataStore<Token>
) : RemoteMediator<Int,FollowerInfoDB>() {

    private val searchFollowerDao= paging3Database.searchFollowerDao()
    private val RemoteKeyDao = paging3Database.RemoteKeyDao()
    @OptIn(DelicateCoroutinesApi::class)
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, FollowerInfoDB>
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
            val responseMapList = mutableListOf<FollowerInfoDB>()
            var response : SearchFollowersDto? = null
               try {
                    response = myApi.getSearchFollowers(
                       accessToken = accessToken, page = currentPage, nickname = searchText
                   )
                       val responseList = response.result?.memberSimpleDtoList
                      if(responseList!=null) {
                          responseList.forEachIndexed { index, followerInfo ->
                              responseMapList.add(
                                  FollowerInfoDB(
                                      index = (currentPage - 1) * Constants.ITEMS_PER_PAGE + index,
                                      createdAt = followerInfo.createdAt,
                                      nickname = followerInfo.nickname,
                                      profileUrl = followerInfo.profileUrl,
                                      memberId = followerInfo.memberId,
                                  )
                              )

                          }
                      }else{
                          searchFollowerDao.deleteItems()
                          RemoteKeyDao.deleteRemoteKeys()
                      }
               }catch (e: HttpException) {
                   val errorBody = e.response()?.errorBody()
                   val errorCode = errorBody?.getErrorCode()

                   errorCode?.let {
                       Log.e("errorcode in friendlist", errorCode.toString())
                   }
               }


            val endOfPaginationReached = response?.result!!.isLast

            val prevPage = if(currentPage == 1) null else currentPage -1
            val nextPage = if(endOfPaginationReached) null else currentPage + 1

                paging3Database.withTransaction {
                    if(loadType == LoadType.REFRESH){
                        searchFollowerDao.deleteItems()
                        RemoteKeyDao.deleteRemoteKeys()
                    }
                    val keys = response.result!!.memberSimpleDtoList.map { items ->
                        RemoteKeys(
                            id = items.memberId,
                            prevPage = prevPage,
                            nextPage = nextPage
                        )
                    }
                    RemoteKeyDao.addAllRemoteKeys(remoteKeys = keys)
                    searchFollowerDao.addItems(items = responseMapList)

                }

                MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        }catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }




    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, FollowerInfoDB>
    ): RemoteKeys? {
        //state.anchorposition은 현재 화면에 보이는 첫 번째 아이템의 포지션
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.memberId?.let { id ->
                RemoteKeyDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, FollowerInfoDB>
    ): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { item ->
                RemoteKeyDao.getRemoteKeys(id = item.memberId)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, FollowerInfoDB>
    ): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { item ->
                RemoteKeyDao.getRemoteKeys(id = item.memberId)
            }
    }



}