package com.zipdabang.zipdabang_android.module.my.data.remote.myrecipes.complete

data class CompleteRecipesResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: CompleteRecipesResult?
)