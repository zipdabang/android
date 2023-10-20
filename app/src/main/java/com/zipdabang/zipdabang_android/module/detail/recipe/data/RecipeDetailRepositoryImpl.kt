package com.zipdabang.zipdabang_android.module.detail.recipe.data

import com.zipdabang.zipdabang_android.common.ResponseBody
import com.zipdabang.zipdabang_android.module.comment.data.remote.UserBlockDto
import com.zipdabang.zipdabang_android.module.detail.recipe.domain.RecipeDetailRepository
import com.zipdabang.zipdabang_android.module.my.data.remote.MyApi
import com.zipdabang.zipdabang_android.module.recipes.data.RecipeApi
import javax.inject.Inject

class RecipeDetailRepositoryImpl @Inject constructor(
    private val recipeApi: RecipeApi,
    private val myApi: MyApi
): RecipeDetailRepository {
    override suspend fun getRecipeDetail(accessToken: String, recipeId: Int): RecipeDetailDto {
        return recipeApi.getRecipeDetail(accessToken, recipeId)
    }

    override suspend fun reportRecipe(
        accessToken: String,
        recipeId: Int,
        reportId: Int
    ): ResponseBody<String?> {
        return recipeApi.reportRecipe(
            accessToken = accessToken,
            recipeId = recipeId,
            reportId = reportId
        )
    }

    override suspend fun deleteRecipe(
        accessToken: String, recipeId: Int
    ): ResponseBody<String?> {
        return recipeApi.deleteRecipe(accessToken, recipeId)
    }

    override suspend fun cancelUserBlock(
        accessToken: String,
        userId: Int
    ): ResponseBody<UserBlockDto?> {
        return myApi.cancelUserBlock(accessToken, userId)
    }
}