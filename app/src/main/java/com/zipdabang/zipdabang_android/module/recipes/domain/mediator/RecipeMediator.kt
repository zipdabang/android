package com.zipdabang.zipdabang_android.module.recipes.domain.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState.Loading.endOfPaginationReached
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.zipdabang.zipdabang_android.core.Paging3Database
import com.zipdabang.zipdabang_android.module.market.data.marketCategory.RemoteKeys
import com.zipdabang.zipdabang_android.module.recipes.data.RecipeApi
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
abstract class RecipeMediator<V: Any>(
    private val recipeApi: RecipeApi,
    private val database: Paging3Database
): RemoteMediator<Int, V>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, V>): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    nextPage
                }
            }

            getResponse(loadType, currentPage)

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    abstract suspend fun getResponse(loadType: LoadType, currentPage: Int)

    abstract suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, V>): RemoteKeys?

    abstract suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, V>): RemoteKeys?

    abstract suspend fun getRemoteKeyForLastItem(state: PagingState<Int, V>): RemoteKeys?
}