package com.zipdabang.zipdabang_android.module.home.domain
import com.zipdabang.zipdabang_android.module.home.data.banner.HomeBannerDto
import com.zipdabang.zipdabang_android.module.home.data.bestrecipe.BestRecipeDto

interface HomeRepository {
    suspend fun getHomeBanner(token : String) : HomeBannerDto
    suspend fun getBestRecipes(token : String) : BestRecipeDto

}