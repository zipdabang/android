package com.zipdabang.zipdabang_android.module.recipes.domain

import com.zipdabang.zipdabang_android.module.recipes.data.banner.RecipeBannerDto

interface RecipeBannerRepository {
    suspend fun getRecipeBanners(accessToken: String): RecipeBannerDto
}