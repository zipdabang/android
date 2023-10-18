package com.zipdabang.zipdabang_android.module.my.data.remote.recipeedit.temp

data class GetTempRecipeResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: GetTempRecipeResult?
)