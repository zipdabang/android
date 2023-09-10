package com.zipdabang.zipdabang_android.module.detail.recipe.data

import com.zipdabang.zipdabang_android.module.detail.recipe.domain.RecipeDetailRepository
import com.zipdabang.zipdabang_android.module.recipes.data.RecipeApi
import javax.inject.Inject

class RecipeDetailRepositoryImpl @Inject constructor(
    private val recipeApi: RecipeApi
): RecipeDetailRepository {
    override suspend fun getRecipeDetail(accessToken: String, recipeId: Int): RecipeDetailDto {
        return recipeApi.getRecipeDetail(accessToken, recipeId)
    }
}