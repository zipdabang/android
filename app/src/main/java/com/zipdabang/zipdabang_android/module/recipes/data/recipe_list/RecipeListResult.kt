package com.zipdabang.zipdabang_android.module.recipes.data.recipe_list

import com.zipdabang.zipdabang_android.module.recipes.data.common.RecipeItem
import kotlinx.serialization.Serializable

@Serializable
data class RecipeListResult(
    val currentPageElements: Int,
    val isFirst: Boolean,
    val isLast: Boolean,
    val recipeList: List<RecipeItem>,
    val totalElements: Int,
    val totalPage: Int
)