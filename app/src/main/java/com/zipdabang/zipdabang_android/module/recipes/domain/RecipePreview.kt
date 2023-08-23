package com.zipdabang.zipdabang_android.module.recipes.domain

import com.zipdabang.zipdabang_android.module.recipes.data.preview.RecipeItem

data class RecipePreview(
    val code: Int,
    val isSuccessful: Boolean,
    val message: String,
    val recipeList: List<RecipeItem>?
)
