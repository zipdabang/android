package com.zipdabang.zipdabang_android.module.login.ui

import com.zipdabang.zipdabang_android.module.login.domain.ZipdabangToken

data class AuthState(
    val isLoading: Boolean = false,
    val zipdabangToken: ZipdabangToken? = null,
    val error: String? = null
)