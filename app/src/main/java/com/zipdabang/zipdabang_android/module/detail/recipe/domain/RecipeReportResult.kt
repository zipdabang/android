package com.zipdabang.zipdabang_android.module.detail.recipe.domain

data class RecipeReportResult(
    val code: Int,
    val message: String,
    val isConnectionSuccessful: Boolean,
    val isReportSuccessful: Boolean
)
