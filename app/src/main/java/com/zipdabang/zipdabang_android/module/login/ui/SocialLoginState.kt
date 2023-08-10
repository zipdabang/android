package com.zipdabang.zipdabang_android.module.login.ui

// Google & Kakao Sign In Return Type
data class SocialLoginResult(
    val data: UserLoginInfo?,
    val error: String? = null
)

data class UserLoginInfo(
    val profile: String?,
    val email: String?
)
