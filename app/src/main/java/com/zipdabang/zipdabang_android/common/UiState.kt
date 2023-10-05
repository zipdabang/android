package com.zipdabang.zipdabang_android.common

import java.io.Serializable

data class UiState<T: Any?>(
    val isLoading: Boolean? = false,
    val errorMessage: String? = null,
    val isSuccessful: Boolean? = null,
    val data: T? = null
): Serializable
