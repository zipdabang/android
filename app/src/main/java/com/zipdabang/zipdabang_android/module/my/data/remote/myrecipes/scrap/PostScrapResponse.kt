package com.zipdabang.zipdabang_android.module.my.data.remote.myrecipes.scrap

data class PostScrapResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: PostScrapResult?
)