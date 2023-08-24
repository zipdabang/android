package com.zipdabang.zipdabang_android.module.recipes.data.preview

import com.zipdabang.zipdabang_android.module.recipes.data.RecipeApi
import com.zipdabang.zipdabang_android.module.recipes.data.recipe_list.RecipeListDto
import com.zipdabang.zipdabang_android.module.recipes.domain.RecipeListRepository

class RecipeListRepositoryImpl(
    private val recipeApi: RecipeApi
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

    override suspend fun getRecipeListByOwnerType(
        accessToken: String,
        ownerType: String,
        order: String,
        pageIndex: Int
    ): RecipeListDto {
        return recipeApi.getRecipeListByOwnerType(
            accessToken = accessToken,
            ownerType = ownerType,
            order = order,
            pageIndex = pageIndex
        )
    }

    override suspend fun getRecipeListByCategory(
        accessToken: String,
        categoryId: Int,
        order: String,
        pageIndex: Int
    ): RecipeListDto {
        return recipeApi.getRecipeListByCategory(
            accessToken = accessToken,
            categoryId = categoryId,
            order = order,
            pageIndex = pageIndex
        )
    }
}