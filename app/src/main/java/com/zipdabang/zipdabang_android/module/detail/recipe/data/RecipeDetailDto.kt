package com.zipdabang.zipdabang_android.module.detail.recipe.data

import kotlinx.serialization.Serializable

@Serializable
data class RecipeDetailDto(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: RecipeDetailResult?
)