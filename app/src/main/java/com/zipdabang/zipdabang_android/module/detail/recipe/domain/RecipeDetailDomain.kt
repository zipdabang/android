package com.zipdabang.zipdabang_android.module.detail.recipe.domain

import com.zipdabang.zipdabang_android.module.detail.recipe.data.Ingredient
import com.zipdabang.zipdabang_android.module.detail.recipe.data.RecipeInfo
import com.zipdabang.zipdabang_android.module.detail.recipe.data.RecipeStep

data class RecipeDetailDomain(
    val code: Int,
    val isSuccessful: Boolean,
    val message: String,
    val ownerId: Int?,
    val isOwner: Boolean?,
    val profileUrl: String?,
    val recipeInfo: RecipeInfo?,
    val recipeSteps: List<RecipeStep>?,
    val recipeIngredients: List<Ingredient>?
)
