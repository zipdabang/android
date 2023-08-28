package com.zipdabang.zipdabang_android.module.detail.recipe.ui

import com.zipdabang.zipdabang_android.module.detail.recipe.domain.RecipeDetailDomain

data class RecipeDetailState (
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val recipeDetailData: RecipeDetailDomain? = null
)