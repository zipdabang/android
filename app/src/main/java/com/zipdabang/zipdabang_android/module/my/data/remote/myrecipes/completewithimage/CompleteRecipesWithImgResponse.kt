package com.zipdabang.zipdabang_android.module.my.data.remote.myrecipes.completewithimage

data class CompleteRecipesWithImgResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: CompleteRecipesWithImgResult?
)