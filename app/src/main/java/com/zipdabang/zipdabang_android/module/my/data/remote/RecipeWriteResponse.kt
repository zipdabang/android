package com.zipdabang.zipdabang_android.module.my.data.remote

data class RecipeWriteResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: String
)