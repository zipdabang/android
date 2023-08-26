package com.zipdabang.zipdabang_android.module.recipes.data.category

import com.zipdabang.zipdabang_android.module.recipes.data.RecipeApi
import com.zipdabang.zipdabang_android.module.recipes.domain.RecipeCategoryRepository

class RecipeCategoryRepositoryImpl(
    private val recipeApi: RecipeApi
): RecipeCategoryRepository {
    override suspend fun getCategoryItems(accessToken: String): RecipeCategoryDto {
        return recipeApi.getRecipeCategory(accessToken)
    }
}