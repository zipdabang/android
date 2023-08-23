package com.zipdabang.zipdabang_android.module.recipes.domain

data class PreferenceToggle(
    val code: Int,
    val isSuccessful: Boolean,
    val message: String,
    val recipeId: Int?
)
