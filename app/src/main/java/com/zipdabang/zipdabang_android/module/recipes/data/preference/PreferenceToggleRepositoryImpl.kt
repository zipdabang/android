package com.zipdabang.zipdabang_android.module.recipes.data.preference

import com.zipdabang.zipdabang_android.module.recipes.data.RecipeApi
import com.zipdabang.zipdabang_android.module.recipes.domain.PreferenceToggleRepository

class PreferenceToggleRepositoryImpl(
    private val recipeApi: RecipeApi,
): PreferenceToggleRepository {
    override suspend fun toggleLike(
        accessToken: String,
        recipeId: Int
    ): PreferenceResultDto {
        return recipeApi.toggleLike(
            accessToken = accessToken,
            recipeId = recipeId
        )
    }

    override suspend fun toggleScrap(
        accessToken: String,
        recipeId: Int
    ): PreferenceResultDto {
        return recipeApi.toggleScrap(
            accessToken = accessToken,
            recipeId = recipeId
        )
    }
}