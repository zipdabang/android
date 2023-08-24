package com.zipdabang.zipdabang_android.module.recipes.ui

import com.zipdabang.zipdabang_android.module.recipes.data.category.BeverageCategory

data class RecipeCategoryState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val recipeCategories: List<BeverageCategory>? = emptyList()
)
