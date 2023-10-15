package com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.following

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.zipdabang.zipdabang_android.common.TabItem
import com.zipdabang.zipdabang_android.core.Paging3Database
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.core.remotekey.RemoteKeys
import com.zipdabang.zipdabang_android.module.my.data.remote.MyApi
import com.zipdabang.zipdabang_android.module.my.ui.component.FollowItem
import com.zipdabang.zipdabang_android.module.search.data.dto.common.SearchRecipe
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.first
import java.lang.Exception
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class FollowingMediator @Inject constructor(
    private val myApi: MyApi,
    private val paging3Database: Paging3Database,
    private val tokenDataStore: DataStore<Token>
) : RemoteMediator<Int, Follower>() {

    private val followingDao = paging3Database.followingDao()
    private val RemoteKeyDao = paging3Database.RemoteKeyDao()

    @OptIn(DelicateCoroutinesApi::class)
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Follower>
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
            val responseMapList = mutableListOf<Follower>()

            val response = myApi.getFollowers(
                accessToken = accessToken,
                page = currentPage
            )
            if (response.result == null) {
                followingDao.deleteItems()
                RemoteKeyDao.deleteRemoteKeys()

                MediatorResult.Success(endOfPaginationReached = true)

            } else {
                val responseList = response.result.followerList
                Log.e("Success SearchCategoryMediator", response.code.toString())

                responseList.forEachIndexed { index, follower ->
                    responseMapList.add(
                        Follower(
                            caption = follower.caption,
                            id = follower.id,
                            imageUrl = follower.imageUrl,
                            isFollowing = follower.isFollowing,
                            nickname = follower.nickname
                        )
                    )

                }


            }


            if (!response.isSuccess) {
                Log.e("Error in SearchCategoryMediator", response.message)
            }

            val endOfPaginationReached = response.result!!.isLast

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            paging3Database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    followingDao.deleteItems()
                    RemoteKeyDao.deleteRemoteKeys()
                }
                val keys = response.result.followerList.map { items ->
                    RemoteKeys(
                        id = items.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                RemoteKeyDao.addAllRemoteKeys(remoteKeys = keys)
                followingDao.addItems(items = responseMapList)

            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }


    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Follower>
    ): RemoteKeys? {
        //state.anchorposition은 현재 화면에 보이는 첫 번째 아이템의 포지션
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                RemoteKeyDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Follower>
    ): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { item ->
                RemoteKeyDao.getRemoteKeys(id = item.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Follower>
    ): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { item ->
                RemoteKeyDao.getRemoteKeys(id = item.id)
            }
    }
}
