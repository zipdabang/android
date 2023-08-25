package com.zipdabang.zipdabang_android.module.home.data

import retrofit2.http.GET
import retrofit2.http.Header

interface HomeAPi {

    @GET("/members/recipes/week-best")
    suspend fun getBestRecipes(
        @Header("Authorization") accessToken : String
    )




}