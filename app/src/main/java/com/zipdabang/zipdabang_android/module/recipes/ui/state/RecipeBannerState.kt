package com.zipdabang.zipdabang_android.module.recipes.ui.state

import com.zipdabang.zipdabang_android.module.recipes.data.banner.BannerImageItem

data class RecipeBannerState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val banners: List<BannerImageItem>? = emptyList()
)
