package com.zipdabang.zipdabang_android.module.my.data.remote.myrecipes.like

data class LikeRecipesResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: LikeRecipesResult?
)