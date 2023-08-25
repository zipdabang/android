package com.zipdabang.zipdabang_android.module.recipes.data.category

import kotlinx.serialization.Serializable

@Serializable
data class BeverageCategory(
    val categoryName: String,
    val id: Int,
    val imageUrl: String
)