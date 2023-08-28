package com.zipdabang.zipdabang_android.module.detail.recipe.data

import kotlinx.serialization.Serializable

@Serializable
data class RecipeDetailResult(
    val ingredients: List<Ingredient>,
    val owner: Boolean,
    val ownerImage: String,
    val recipeInfo: RecipeInfo,
    val steps: List<RecipeStep>
)