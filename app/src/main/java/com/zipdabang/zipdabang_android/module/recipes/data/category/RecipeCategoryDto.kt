package com.zipdabang.zipdabang_android.module.recipes.data.category

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeCategoryDto(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    @SerialName("result")
    val recipeCategoryResult: RecipeCategoryResult?
)