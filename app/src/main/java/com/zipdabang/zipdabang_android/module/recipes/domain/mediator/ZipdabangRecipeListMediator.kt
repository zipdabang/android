package com.zipdabang.zipdabang_android.module.recipes.domain.mediator

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.zipdabang.zipdabang_android.common.Constants.TOKEN_NULL
import com.zipdabang.zipdabang_android.core.Paging3Database
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.core.remotekey.RemoteKeys
import com.zipdabang.zipdabang_android.core.storage.recipe.RecipeDatabase
import com.zipdabang.zipdabang_android.entity.recipe.RecipeZipdabangEntity
import com.zipdabang.zipdabang_android.entity.recipe.RemoteKeysEntity
import com.zipdabang.zipdabang_android.module.recipes.common.OwnerType
import com.zipdabang.zipdabang_android.module.recipes.data.RecipeApi
import com.zipdabang.zipdabang_android.module.recipes.data.common.RecipeItem
import com.zipdabang.zipdabang_android.module.recipes.data.local.RecipeItemEntity
import com.zipdabang.zipdabang_android.module.recipes.mapper.toRecipeItemEntity
import com.zipdabang.zipdabang_android.module.recipes.mapper.toRecipeZipdabangEntity
import kotlinx.coroutines.flow.first
@OptIn(ExperimentalPagingApi::class)
class ZipdabangRecipeListMediator(
    private val recipeApi: RecipeApi,
    private val database: RecipeDatabase,
    private val datastore: DataStore<Token>,
    private val orderBy: String,
    private val isNetworkAvailable: Boolean
): RemoteMediator<Int, RecipeZipdabangEntity>() {

    private val ownerType = OwnerType.OFFICIAL.type

    private val recipeListDao = database.recipeZipdabangDao()
    private val remoteKeyDao = database.recipeRemoteKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RecipeZipdabangEntity>
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

            val response = recipeApi.getRecipeListByOwnerType(
                accessToken = accessToken,
                ownerType = ownerType,
                order = orderBy,
                pageIndex = currentPage
            )?.result?.recipeList?.map {
                it.toRecipeZipdabangEntity()
            } ?: emptyList()

            val endOfPaginationReached = response.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            database.withTransaction {
                if (isNetworkAvailable) {
                    if (loadType == LoadType.REFRESH) {
                        recipeListDao.deleteAllRecipes()
                        remoteKeyDao.deleteRemoteKeys()
                    }

                    recipeListDao.addRecipes(recipes = response)

                    val keys = response.map { recipeItem ->
                        val itemId = recipeListDao.getItemIdByRecipeId(recipeItem.recipeId)
                        RemoteKeysEntity(
                            id = itemId,
                            prevPage = prevPage,
                            nextPage = nextPage
                        )
                    }
                    remoteKeyDao.addAllRemoteKeys(remoteKeys = keys)
                } else {
                    return@withTransaction
                }
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, RecipeZipdabangEntity>): RemoteKeysEntity? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull() ?.let { recipeItem ->
            remoteKeyDao.getRemoteKeys(id = recipeItem.itemId)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, RecipeZipdabangEntity>): RemoteKeysEntity? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { recipeItem ->
            remoteKeyDao.getRemoteKeys(id = recipeItem.itemId)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, RecipeZipdabangEntity>): RemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.itemId?.let { itemId ->
                remoteKeyDao.getRemoteKeys(id = itemId)
            }
        }
    }
}