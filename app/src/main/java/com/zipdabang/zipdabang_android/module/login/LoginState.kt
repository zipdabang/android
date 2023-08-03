package com.zipdabang.zipdabang_android.module.login

data class LoginState(
    val loading: Boolean = true,
    val userLoginInfo: UserLoginInfo? = null,
    val error: Boolean = false
)
