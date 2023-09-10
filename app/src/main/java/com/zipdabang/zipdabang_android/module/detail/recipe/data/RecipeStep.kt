package com.zipdabang.zipdabang_android.module.detail.recipe.data

import kotlinx.serialization.Serializable

@Serializable
data class RecipeStep(
    val description: String,
    val image: String,
    val stepNum: Int
)