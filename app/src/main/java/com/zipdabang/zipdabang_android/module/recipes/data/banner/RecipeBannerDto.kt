package com.zipdabang.zipdabang_android.module.recipes.data.banner

import kotlinx.serialization.Serializable

@Serializable
data class RecipeBannerDto(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: BannerResult
)