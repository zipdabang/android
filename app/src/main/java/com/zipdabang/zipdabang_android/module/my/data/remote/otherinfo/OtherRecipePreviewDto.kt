package com.zipdabang.zipdabang_android.module.my.data.remote.otherinfo

data class OtherRecipePreviewDto(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: OtherRecipePreviewResult
)