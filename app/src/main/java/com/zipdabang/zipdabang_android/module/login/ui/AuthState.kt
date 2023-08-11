package com.zipdabang.zipdabang_android.module.login.ui

data class AuthState(
    val isLoading: Boolean = false,
    val token: String? = null,
    val error: String? = null
)