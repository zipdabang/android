package com.zipdabang.zipdabang_android.module.recipes.domain

import com.zipdabang.zipdabang_android.module.recipes.data.banner.BannerImageItem

data class RecipeBanner(
    val code: Int,
    val isSuccessful: Boolean,
    val message: String,
    val recipeBanners: List<BannerImageItem>?
)
