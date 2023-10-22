package com.zipdabang.zipdabang_android.module.my.data.remote.recipeedit.complete

data class GetCompleteRecipeResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: GetCompleteRecipeResult
)