package com.zipdabang.zipdabang_android.module.recipes.ui

import com.zipdabang.zipdabang_android.module.recipes.data.preview.RecipeItem

data class RecipePreviewState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    var data: List<RecipeItem>? = emptyList()
)