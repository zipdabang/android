package com.zipdabang.zipdabang_android.module.home.data.bestrecipe

data class BestRecipeDto(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: RecipeResult
)