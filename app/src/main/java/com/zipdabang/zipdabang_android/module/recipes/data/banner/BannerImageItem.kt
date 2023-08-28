package com.zipdabang.zipdabang_android.module.recipes.data.banner

import kotlinx.serialization.Serializable

@Serializable
data class BannerImageItem(
    val imageUrl: String,
    val order: Int
)