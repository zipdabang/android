package com.zipdabang.zipdabang_android.module.recipes.data.banner

import com.zipdabang.zipdabang_android.module.recipes.data.RecipeApi
import com.zipdabang.zipdabang_android.module.recipes.domain.RecipeBannerRepository

class RecipeBannerRepositoryImpl(
    private val recipeApi: RecipeApi
): RecipeBannerRepository {
    override suspend fun getRecipeBanners(accessToken: String): RecipeBannerDto? {
        return recipeApi.getRecipeBanners(accessToken)
    }
}