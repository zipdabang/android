package com.zipdabang.zipdabang_android.module.recipes.data.banner

import kotlinx.serialization.Serializable

@Serializable
data class BannerResult(
    val bannerList: List<BannerImageItem>,
    val size: Int
)