package com.zipdabang.zipdabang_android.module.my.data.remote.myrecipes.preview

data class CompleteRecipesWithImgPreviewResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: CompleteRecipesWithImgResult?
)