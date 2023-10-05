package com.zipdabang.zipdabang_android.module.recipes.data.hot

import kotlinx.serialization.Serializable

@Serializable
data class HotRecipeDto(
    val recipeList: List<HotRecipeItem>,
    val categoryId: Int,
    val totalElements: Int
)
