package com.zipdabang.zipdabang_android.module.my.data.remote.myinfo

data class MyInfoRecipesResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: MyInfoRecipesResult
)