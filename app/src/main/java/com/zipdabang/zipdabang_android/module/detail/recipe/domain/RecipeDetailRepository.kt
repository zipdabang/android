package com.zipdabang.zipdabang_android.module.detail.recipe.domain

import com.zipdabang.zipdabang_android.module.detail.recipe.data.RecipeDetailDto

interface RecipeDetailRepository {
    suspend fun getRecipeDetail(accessToken: String, recipeId: Int): RecipeDetailDto
}