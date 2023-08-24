package com.zipdabang.zipdabang_android.module.recipes.domain

import com.zipdabang.zipdabang_android.module.recipes.data.preview.RecipePreviewItemsDto
import com.zipdabang.zipdabang_android.module.recipes.data.recipe_list.RecipeListDto

interface RecipeListRepository {
    suspend fun getRecipePreviewList(accessToken: String, ownerType: String): RecipePreviewItemsDto

    suspend fun getRecipeListByOwnerType(
        accessToken: String,
        ownerType: String,
        order: String,
        pageIndex: Int
    ): RecipeListDto

    suspend fun getRecipeListByCategory(
        accessToken: String,
        categoryId: Int,
        order: String,
        pageIndex: Int
    ): RecipeListDto
}