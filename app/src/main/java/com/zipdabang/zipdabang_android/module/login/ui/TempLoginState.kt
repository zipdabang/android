package com.zipdabang.zipdabang_android.module.login.ui

data class TempLoginState(
    val isLoading: Boolean = false,
    val accessToken: String? = null,
    val errorMessage: String? = null
)
