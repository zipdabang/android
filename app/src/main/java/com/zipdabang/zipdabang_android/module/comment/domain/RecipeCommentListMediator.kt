package com.zipdabang.zipdabang_android.module.comment.domain

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
import com.zipdabang.zipdabang_android.module.comment.data.local.RecipeCommentEntity
import com.zipdabang.zipdabang_android.module.comment.util.toRecipeCommentEntity
import com.zipdabang.zipdabang_android.module.recipes.data.RecipeApi
import com.zipdabang.zipdabang_android.module.recipes.data.common.RecipeItem
import com.zipdabang.zipdabang_android.module.recipes.data.local.RecipeItemEntity
import com.zipdabang.zipdabang_android.module.recipes.mapper.toRecipeItemEntity
import kotlinx.coroutines.flow.first

@OptIn(ExperimentalPagingApi::class)
class RecipeCommentListMediator(
    private val recipeApi: RecipeApi,
    private val database: Paging3Database,
    private val datastore: DataStore<Token>,
    private val recipeId: Int
): RemoteMediator<Int, RecipeCommentEntity>() {

    private val recipeCommentDao = database.recipeCommentDao()
    private val remoteKeyDao = database.RemoteKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RecipeCommentEntity>
    ): MediatorResult {
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

            val accessToken = ("Bearer " + datastore.data.first().accessToken)

            val response = recipeApi.getRecipeComments(
                accessToken = accessToken,
                recipeId = recipeId,
                pageIndex = currentPage
            ).result?.commentList?.map {
                it.toRecipeCommentEntity()
            } ?: emptyList()

            val endOfPaginationReached = response.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    recipeCommentDao.deleteAllComments()
                    remoteKeyDao.deleteRemoteKeys()
                }

                recipeCommentDao.addComments(comments = response)

                val keys = response.map { commentItem ->
                    val itemId = recipeCommentDao.getItemIdByRecipeId(commentItem.commentId)
                    RemoteKeys(
                        id = itemId,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }

                remoteKeyDao.addAllRemoteKeys(remoteKeys = keys)

            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, RecipeCommentEntity>): RemoteKeys? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull() ?.let { commentItem ->
            remoteKeyDao.getRemoteKeys(id = commentItem.itemId)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, RecipeCommentEntity>): RemoteKeys? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { commentItem ->
            remoteKeyDao.getRemoteKeys(id = commentItem.itemId)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, RecipeCommentEntity>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.itemId?.let { itemId ->
                remoteKeyDao.getRemoteKeys(id = itemId)
            }
        }
    }
}