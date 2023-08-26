package com.zipdabang.zipdabang_android.module.home.data

import com.zipdabang.zipdabang_android.module.home.data.bestreciepe.BestRecipeDto
import retrofit2.http.GET
import retrofit2.http.Header

interface HomeApi {

    @GET("/members/recipes/week-best")
    suspend fun getBestRecipes(
        @Header("Authorization") accessToken : String
    ) : BestRecipeDto


}