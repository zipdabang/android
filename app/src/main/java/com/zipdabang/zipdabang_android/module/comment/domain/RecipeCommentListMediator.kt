package com.zipdabang.zipdabang_android.module.comment.domain

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.core.remotekey.RemoteKeys
import com.zipdabang.zipdabang_android.core.storage.recipe.RecipeDatabase
import com.zipdabang.zipdabang_android.entity.recipe.RecipeCommentsEntity
import com.zipdabang.zipdabang_android.entity.recipe.RemoteKeysEntity
import com.zipdabang.zipdabang_android.module.comment.data.local.RecipeCommentEntity
import com.zipdabang.zipdabang_android.module.comment.util.recipeCommentsEntity
import com.zipdabang.zipdabang_android.module.recipes.data.RecipeApi
import kotlinx.coroutines.flow.first

@OptIn(ExperimentalPagingApi::class)
class RecipeCommentListMediator(
    private val recipeApi: RecipeApi,
    private val database: RecipeDatabase,
    private val datastore: DataStore<Token>,
    private val recipeId: Int
): RemoteMediator<Int, RecipeCommentsEntity>() {

    companion object {
        const val TAG = "RecipeCommentListMediator"
    }

    private val recipeCommentDao = database.recipeCommentsDao()
    private val remoteKeyDao = database.recipeRemoteKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RecipeCommentsEntity>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    Log.i(TAG, "refresh")
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
                it.recipeCommentsEntity()
            } ?: emptyList()

            Log.d("comments list", "$response")

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
                    Log.d("commentItem id", "${commentItem.commentId}")
                    val itemId = recipeCommentDao.getItemIdByCommentId(commentItem.commentId)
                    RemoteKeysEntity(
                        id = itemId,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }

                remoteKeyDao.addAllRemoteKeys(remoteKeys = keys)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        }
        catch (e: Exception) {
            Log.d("comment list - mediator", "${e.message}")
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, RecipeCommentsEntity>): RemoteKeysEntity? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull() ?.let { commentItem ->
            remoteKeyDao.getRemoteKeys(id = commentItem.itemId)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, RecipeCommentsEntity>): RemoteKeysEntity? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { commentItem ->
            remoteKeyDao.getRemoteKeys(id = commentItem.itemId)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, RecipeCommentsEntity>): RemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.itemId?.let { itemId ->
                remoteKeyDao.getRemoteKeys(id = itemId)
            }
        }
    }
}