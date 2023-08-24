package com.zipdabang.zipdabang_android.module.recipes.data.preference

import kotlinx.serialization.Serializable

@Serializable
data class PreferenceToggleResult(
    val calledAt: String,
    val recipeId: Int
)