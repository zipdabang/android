package com.zipdabang.zipdabang_android.module.splash.ui

import com.zipdabang.zipdabang_android.module.splash.data.NewTokens

data class NewTokenState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isRefreshTokenValid: Boolean? = null,
    val newTokens: NewTokens? = null
)
