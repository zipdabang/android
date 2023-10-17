package com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.follow

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.common.TabItem
import com.zipdabang.zipdabang_android.common.getErrorCode
import com.zipdabang.zipdabang_android.core.Paging3Database
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.core.remotekey.RemoteKeys
import com.zipdabang.zipdabang_android.module.my.data.remote.MyApi
import com.zipdabang.zipdabang_android.module.my.ui.component.FollowItem
import com.zipdabang.zipdabang_android.module.search.data.dto.common.SearchRecipe
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.lang.Exception
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class FollowMediator @Inject constructor(
    private val myApi: MyApi,
    private val paging3Database: Paging3Database,
    private val tokenDataStore: DataStore<Token>
) : RemoteMediator<Int, Following>() {

    private val followDao = paging3Database.followDao()
    private val RemoteKeyDao = paging3Database.RemoteKeyDao()

    @OptIn(DelicateCoroutinesApi::class)
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Following>
    ): MediatorResult {
        return try {
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
            val responseMapList = mutableListOf<Following>()


            var response : FollowDto? = null
            try{
                response = myApi.getFollowings(
                accessToken = accessToken,
                page = currentPage
            )
                val data = response.result!!.followingList
                Log.e("followItem first",data.size.toString())
                data.forEach{ following ->
                    responseMapList.add(
                        Following(
                            caption = following.caption,
                            id = following.id,
                            imageUrl = following.imageUrl,
                            nickname = following.nickname
                        )
                    )

                }
                Log.e("followItem second",responseMapList.size.toString())

            }catch (e: HttpException){
                val errorBody = e.response()?.errorBody()
                val errorCode = errorBody?.getErrorCode()

                errorCode?.let {
                    Log.e("errorcode in friendlist",errorCode.toString())
                    if(errorCode == 4055) {
                        followDao.deleteItems()
                        RemoteKeyDao.deleteRemoteKeys()
                        MediatorResult.Success(endOfPaginationReached = true)

                    }
                }
            }



            val endOfPaginationReached = response?.result!!.isLast

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            paging3Database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    followDao.deleteItems()
                    RemoteKeyDao.deleteRemoteKeys()
                }
                Log.e("responesMapList",responseMapList.size.toString())

                val keys = responseMapList.map { items ->
                    RemoteKeys(
                        id = items.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                Log.e("responesMapList",keys.size.toString())


                RemoteKeyDao.addAllRemoteKeys(remoteKeys = keys)
                followDao.addItems(items = responseMapList)

            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }


    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Following>
    ): RemoteKeys? {
        //state.anchorposition은 현재 화면에 보이는 첫 번째 아이템의 포지션
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                RemoteKeyDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Following>
    ): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { item ->
                RemoteKeyDao.getRemoteKeys(id = item.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Following>
    ): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { item ->
                RemoteKeyDao.getRemoteKeys(id = item.id)
            }
    }
}
