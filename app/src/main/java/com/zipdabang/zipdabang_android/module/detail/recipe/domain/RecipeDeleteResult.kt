package com.zipdabang.zipdabang_android.module.detail.recipe.domain

data class RecipeDeleteResult(
    val code: Int,
    val message: String,
    val isConnectionSuccessful: Boolean,
    val isDeleteSuccessful: Boolean
)
