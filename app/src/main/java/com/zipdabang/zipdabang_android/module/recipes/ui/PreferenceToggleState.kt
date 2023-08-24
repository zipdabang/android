package com.zipdabang.zipdabang_android.module.recipes.ui

import com.zipdabang.zipdabang_android.module.recipes.data.preview.RecipeItem

data class PreferenceToggleState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccessful: Boolean? = null
)