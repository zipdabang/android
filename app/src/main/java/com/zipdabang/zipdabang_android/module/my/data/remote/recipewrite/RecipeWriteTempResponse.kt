package com.zipdabang.zipdabang_android.module.my.data.remote.recipewrite

data class RecipeWriteTempResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: RecipeWriteTempResult?
)