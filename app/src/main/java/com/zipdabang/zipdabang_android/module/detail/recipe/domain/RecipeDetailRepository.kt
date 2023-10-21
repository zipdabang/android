package com.zipdabang.zipdabang_android.module.detail.recipe.domain

import com.zipdabang.zipdabang_android.common.ResponseBody
import com.zipdabang.zipdabang_android.module.comment.data.remote.UserBlockDto
import com.zipdabang.zipdabang_android.module.detail.recipe.data.RecipeDetailDto

interface RecipeDetailRepository {
    suspend fun getRecipeDetail(
        accessToken: String, recipeId: Int
    ): RecipeDetailDto

    suspend fun reportRecipe(
        accessToken: String, recipeId: Int, reportId: Int
    ): ResponseBody<String?>

    suspend fun deleteRecipe(
        accessToken: String, recipeId: Int
    ): ResponseBody<String?>

    suspend fun cancelUserBlock(
        accessToken: String, userId: Int
    ): ResponseBody<UserBlockDto?>
}