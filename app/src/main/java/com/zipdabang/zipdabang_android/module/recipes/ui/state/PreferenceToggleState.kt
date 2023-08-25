package com.zipdabang.zipdabang_android.module.recipes.ui.state

data class PreferenceToggleState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccessful: Boolean? = null
)