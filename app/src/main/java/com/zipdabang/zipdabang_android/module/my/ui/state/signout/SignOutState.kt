package com.zipdabang.zipdabang_android.module.my.ui.state.signout

data class SignOutState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccessful: Boolean? = null
)
