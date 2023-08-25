package com.zipdabang.zipdabang_android.module.recipes.domain

import androidx.paging.PagingData
import com.zipdabang.zipdabang_android.module.recipes.common.OwnerType
import com.zipdabang.zipdabang_android.module.recipes.data.common.RecipeItem
import com.zipdabang.zipdabang_android.module.recipes.data.preference.PreferenceResultDto
import com.zipdabang.zipdabang_android.module.recipes.data.preview.RecipePreviewItemsDto
import com.zipdabang.zipdabang_android.module.recipes.data.recipe_list.RecipeListDto
import kotlinx.coroutines.flow.Flow

interface RecipeListRepository {
    suspend fun getRecipePreviewList(accessToken: String, ownerType: String): RecipePreviewItemsDto

    fun getRecipeListByOwnerType(
        ownerType: OwnerType,
        orderBy: String,
    ): Flow<PagingData<RecipeItem>>

    fun getRecipeListByCategory(
        categoryId: Int,
        orderBy: String
    ): Flow<PagingData<RecipeItem>>

    suspend fun toggleLikeRemote(
        accessToken: String,
        recipeId: Int
    ): PreferenceResultDto

    suspend fun toggleLikeLocal(
        recipeId: Int,
        preferencesResultDto: PreferenceResultDto
    ): PreferenceResultDto

    suspend fun toggleScrapRemote(
        accessToken: String,
        recipeId: Int
    ): PreferenceResultDto

    suspend fun toggleScrapLocal(
        recipeId: Int,
        preferencesResultDto: PreferenceResultDto
    ): PreferenceResultDto
}