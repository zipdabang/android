package com.zipdabang.zipdabang_android.module.recipes.data.preview

import com.zipdabang.zipdabang_android.module.recipes.data.RecipeApi
import com.zipdabang.zipdabang_android.module.recipes.data.preference.PreferenceResultDto
import com.zipdabang.zipdabang_android.module.recipes.domain.RecipePreviewRepository

class RecipePreviewRepositoryImpl(
    private val recipeApi: RecipeApi
): RecipePreviewRepository {
    override suspend fun getRecipePreviewList(
        accessToken: String,
        ownerType: String
    ): RecipePreviewItemsDto {
        return recipeApi.getRecipePreview(
            accessToken = accessToken,
            ownerType = ownerType
        )
    }
}