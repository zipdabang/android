package com.zipdabang.zipdabang_android.module.recipes.data.preview

import kotlinx.serialization.Serializable

@Serializable
data class RecipeResult(
    val recipeList: List<RecipeItem>,
    val totalElements: Int
)