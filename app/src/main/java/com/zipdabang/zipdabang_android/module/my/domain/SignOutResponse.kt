package com.zipdabang.zipdabang_android.module.my.domain

data class SignOutResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val isSignOutSuccessful: Boolean
)
