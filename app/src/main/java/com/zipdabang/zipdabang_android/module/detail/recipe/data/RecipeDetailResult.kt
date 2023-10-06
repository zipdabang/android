package com.zipdabang.zipdabang_android.module.detail.recipe.data

import kotlinx.serialization.Serializable

@Serializable
data class RecipeDetailResult(
    val ingredients: List<Ingredient>,
    val owner: Boolean,
    val recipeInfo: RecipeInfo,
    val ownerId: Int,
    val steps: List<RecipeStep>
)