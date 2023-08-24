package com.zipdabang.zipdabang_android.module.recipes.data.preview

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipePreviewItemsDto(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    @SerialName("result")
    val recipeResult: RecipeResult?
)