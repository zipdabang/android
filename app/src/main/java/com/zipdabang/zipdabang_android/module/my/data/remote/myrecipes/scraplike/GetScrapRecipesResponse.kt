package com.zipdabang.zipdabang_android.module.my.data.remote.myrecipes.scraplike

data class GetScrapRecipesResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: GetScrapRecipesResult?
)