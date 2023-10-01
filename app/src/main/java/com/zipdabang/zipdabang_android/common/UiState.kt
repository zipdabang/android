package com.zipdabang.zipdabang_android.common

data class UiState<T: Any?>(
    val isLoading: Boolean? = false,
    val errorMessage: String? = null,
    val isSuccessful: Boolean? = null,
    val data: T? = null
)
