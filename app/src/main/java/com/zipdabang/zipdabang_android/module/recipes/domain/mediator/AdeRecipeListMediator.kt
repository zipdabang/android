package com.zipdabang.zipdabang_android.module.recipes.domain.mediator

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
import com.zipdabang.zipdabang_android.core.storage.recipe.RecipeDatabase
import com.zipdabang.zipdabang_android.entity.recipe.RecipeAdeEntity
import com.zipdabang.zipdabang_android.entity.recipe.RecipeCoffeeEntity
import com.zipdabang.zipdabang_android.entity.recipe.RemoteKeysEntity
import com.zipdabang.zipdabang_android.module.recipes.data.RecipeApi
import com.zipdabang.zipdabang_android.module.recipes.data.common.RecipeItem
import com.zipdabang.zipdabang_android.module.recipes.data.local.RecipeItemEntity
import com.zipdabang.zipdabang_android.module.recipes.mapper.toRecipeAdeEntity
import com.zipdabang.zipdabang_android.module.recipes.mapper.toRecipeCoffeeEntity
import com.zipdabang.zipdabang_android.module.recipes.mapper.toRecipeItemEntity
import kotlinx.coroutines.flow.first

@OptIn(ExperimentalPagingApi::class)
class AdeRecipeListMediator(
    private val recipeApi: RecipeApi,
    private val database: RecipeDatabase,
    private val datastore: DataStore<Token>,
    private val categoryId: Int = 4,
    private val orderBy: String,
    private val isNetworkAvailable: Boolean
): RemoteMediator<Int, RecipeAdeEntity>() {

    private val recipeListDao = database.recipeAdeDao()
    private val remoteKeyDao = database.recipeRemoteKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RecipeAdeEntity>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    Log.d("RecipeList - Mediator", "${state.anchorPosition}")
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    Log.d("RecipeList - Mediator", "loadtype - refresh, remoteKeys : $remoteKeys")
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    Log.d("RecipeList - Mediator", "loadtype - prepend")
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    Log.d("RecipeList - Mediator", "loadtype - append")
                    nextPage
                }
            }

            val accessToken = ("Bearer " + datastore.data.first().accessToken)
            Log.d("RecipeList - mediator", "accessToken : $accessToken")

            val response = recipeApi.getRecipeListByCategory(
                accessToken = accessToken,
                categoryId = categoryId,
                order = orderBy,
                pageIndex = currentPage
            ).result?.recipeList?.map {
                it.toRecipeAdeEntity()
            } ?: emptyList()

            Log.d("RecipeList - mediator", "current page $currentPage")

            val endOfPaginationReached = response.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            database.withTransaction {
                if (isNetworkAvailable) {
                    Log.d("RecipeList - Mediator", "database transaction entered")
                    if (loadType == LoadType.REFRESH) {
                        Log.d("RecipeList - Mediator", "load type is refresh")
                        recipeListDao.deleteAllRecipes()
                        remoteKeyDao.deleteRemoteKeys()
                    }

                    recipeListDao.addRecipes(recipes = response)

                    Log.d("RecipeList - Mediator", "response size in database ${response?.size}")

                    val keys = response.map { recipeItem ->
                        val itemId = recipeListDao.getItemIdByRecipeId(recipeItem.recipeId)
                        RemoteKeysEntity(
                            id = itemId,
                            prevPage = prevPage,
                            nextPage = nextPage
                        )
                    }
                    Log.d("RecipeList - Mediator", "key size in database ${keys.size}")

                    remoteKeyDao.addAllRemoteKeys(remoteKeys = keys)

                    Log.d("RecipeList - Mediator", "database transaction ended")
                }
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            Log.d("RecipeList - mediator", "error occured : ${e.message} ${e.cause}")
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, RecipeAdeEntity>): RemoteKeysEntity? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull() ?.let { recipeItem ->
            remoteKeyDao.getRemoteKeys(id = recipeItem.itemId)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, RecipeAdeEntity>): RemoteKeysEntity? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { recipeItem ->
            remoteKeyDao.getRemoteKeys(id = recipeItem.itemId)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, RecipeAdeEntity>): RemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.itemId?.let { itemId ->
                remoteKeyDao.getRemoteKeys(id = itemId)
            }
        }
    }
}