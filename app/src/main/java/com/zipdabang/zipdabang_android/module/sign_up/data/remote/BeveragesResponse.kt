package com.zipdabang.zipdabang_android.module.sign_up.data.remote

data class BeveragesResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: BeveragesResult?
)