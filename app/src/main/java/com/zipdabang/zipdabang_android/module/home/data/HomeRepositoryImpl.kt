package com.zipdabang.zipdabang_android.module.home.data

import com.zipdabang.zipdabang_android.module.home.data.banner.HomeBannerDto
import com.zipdabang.zipdabang_android.module.home.data.bestrecipe.BestRecipeDto
import com.zipdabang.zipdabang_android.module.home.domain.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject  constructor(
   private val homeApi: HomeApi
) : HomeRepository {
    override suspend fun getHomeBanner(token: String): HomeBannerDto {
        return homeApi.getHomeBanner(token)
    }

    override suspend fun getBestRecipes(token: String): BestRecipeDto {
       return homeApi.getBestRecipes(token)
    }


}