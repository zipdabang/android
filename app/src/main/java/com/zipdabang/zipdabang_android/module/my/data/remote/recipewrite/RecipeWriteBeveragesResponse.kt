package com.zipdabang.zipdabang_android.module.my.data.remote.recipewrite

data class RecipeWriteBeveragesResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: RecipeWriteBeverageResult?
)