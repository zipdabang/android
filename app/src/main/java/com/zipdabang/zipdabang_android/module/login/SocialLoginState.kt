package com.zipdabang.zipdabang_android.module.login

data class SocialLoginResult(
    val data: UserLoginInfo?,
    val error: String? = null
)

data class UserLoginInfo(
    val profile: String?,
    val email: String?
)
