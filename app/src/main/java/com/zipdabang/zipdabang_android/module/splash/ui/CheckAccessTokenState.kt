package com.zipdabang.zipdabang_android.module.splash.ui

data class CheckAccessTokenState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isCheckSuccessful: Boolean? = null,
    val isAccessTokenValid: Boolean? = null
)
