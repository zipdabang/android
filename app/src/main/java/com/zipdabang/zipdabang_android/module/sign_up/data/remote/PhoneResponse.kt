package com.zipdabang.zipdabang_android.module.sign_up.data.remote

data class PhoneResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: Int
)