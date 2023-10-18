package com.zipdabang.zipdabang_android.module.detail.recipe.ui

import com.zipdabang.zipdabang_android.module.detail.recipe.domain.RecipeDetailDomain

data class RecipeDetailState (
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val recipeDetailData: RecipeDetailDomain? = null
)