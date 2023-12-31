package com.zipdabang.zipdabang_android.module.home.data

import com.zipdabang.zipdabang_android.module.home.data.banner.HomeBannerDto
import com.zipdabang.zipdabang_android.module.home.data.bestrecipe.BestRecipeDto
import retrofit2.http.GET
import retrofit2.http.Header

interface HomeApi {

    @GET("/banners")
    suspend fun getHomeBanner(
        @Header("Authorization") accessToken : String
    ) : HomeBannerDto

    @GET("/members/recipes/week-best")
    suspend fun getBestRecipes(
        @Header("Authorization") accessToken : String
    ) : BestRecipeDto


}