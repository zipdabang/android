package com.zipdabang.zipdabang_android.module.recipes.data.common

import androidx.datastore.core.DataStore
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.room.util.copy
import com.zipdabang.zipdabang_android.common.Constants.ITEMS_PER_PAGE
import com.zipdabang.zipdabang_android.core.Paging3Database
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.recipes.common.OwnerType
import com.zipdabang.zipdabang_android.module.recipes.data.RecipeApi
import com.zipdabang.zipdabang_android.module.recipes.data.preference.PreferenceResultDto
import com.zipdabang.zipdabang_android.module.recipes.data.preference.PreferenceToggleResult
import com.zipdabang.zipdabang_android.module.recipes.data.preview.RecipePreviewItemsDto
import com.zipdabang.zipdabang_android.module.recipes.data.recipe_list.RecipeListDto
import com.zipdabang.zipdabang_android.module.recipes.domain.RecipeListRepository
import com.zipdabang.zipdabang_android.module.recipes.domain.mediator.CategoryRecipeListMediator
import com.zipdabang.zipdabang_android.module.recipes.domain.mediator.OwnerTypeRecipeListMediator
import kotlinx.coroutines.flow.Flow

class RecipeListRepositoryImpl(
    private val recipeApi: RecipeApi,
    private val database: Paging3Database,
    private val dataStore: DataStore<Token>
): RecipeListRepository {
    override suspend fun getRecipePreviewList(
        accessToken: String,
        ownerType: String
    ): RecipePreviewItemsDto {
        return recipeApi.getRecipePreview(
            accessToken = accessToken,
            ownerType = ownerType
        )
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getRecipeListByOwnerType(
        ownerType: String,
        orderBy: String,
    ): Flow<PagingData<RecipeItem>> {
        val pagingSourceFactory = {
            database.recipeListDao().getAllRecipes()
        }

        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = OwnerTypeRecipeListMediator(
                recipeApi = recipeApi,
                database = database,
                datastore = dataStore,
                ownerType = ownerType,
                orderBy = orderBy
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getRecipeListByCategory(
        categoryId: Int,
        orderBy: String
    ): Flow<PagingData<RecipeItem>> {
        val pagingSourceFactory = {
            database.recipeListDao().getAllRecipes()
        }

        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = CategoryRecipeListMediator(
                recipeApi = recipeApi,
                database = database,
                datastore = dataStore,
                categoryId = categoryId,
                orderBy = orderBy
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override suspend fun toggleLikeRemote(
        accessToken: String,
        recipeId: Int
    ): PreferenceResultDto {
        return recipeApi.toggleLike(accessToken, recipeId)
    }

    override suspend fun toggleLikeLocal(
        recipeId: Int,
        preferencesResultDto: PreferenceResultDto
    ): PreferenceResultDto {
        return try {
            val recipeItem = database.recipeListDao().getRecipeItemById(recipeId)
            recipeItem?.let {
                it.isLiked = !it.isLiked
                if (it.isLiked) {
                    it.likes += 1
                } else {
                    it.likes -= 1
                }
                database.recipeListDao().updateRecipe(it)
            }
            preferencesResultDto
        } catch (e: Exception) {
            preferencesResultDto.copy(
                code = e.hashCode(),
                isSuccess = false,
                message = e.message ?: "로컬 처리 중 알 수 없는 에러 발생",
            )
        }
    }

    override suspend fun toggleScrapRemote(
        accessToken: String,
        recipeId: Int
    ): PreferenceResultDto {
        return recipeApi.toggleScrap(accessToken, recipeId)
    }

    override suspend fun toggleScrapLocal(
        recipeId: Int,
        preferencesResultDto: PreferenceResultDto
    ): PreferenceResultDto {
        return try {
            val recipeItem = database.recipeListDao().getRecipeItemById(recipeId)
            recipeItem?.let {
                it.isScrapped = !it.isScrapped
                if (it.isScrapped) {
                    it.scraps += 1
                } else {
                    it.scraps -= 1
                }
                database.recipeListDao().updateRecipe(it)
            }
            preferencesResultDto
        } catch (e: Exception) {
            preferencesResultDto.copy(
                code = e.hashCode(),
                isSuccess = false,
                message = e.message ?: "로컬 처리 중 알 수 없는 에러 발생",
            )
        }
    }
}