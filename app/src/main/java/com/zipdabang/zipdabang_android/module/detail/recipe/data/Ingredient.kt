package com.zipdabang.zipdabang_android.module.detail.recipe.data

import kotlinx.serialization.Serializable

@Serializable
data class Ingredient(
    val ingredientName: String,
    val quantity: String
)