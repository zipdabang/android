package com.zipdabang.zipdabang_android.module.splash.domain

import com.zipdabang.zipdabang_android.module.splash.data.AutoLoginDto
import com.zipdabang.zipdabang_android.module.splash.data.NewTokenDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.QueryMap

interface AutoLoginApi {
    @GET("auto-login")
    suspend fun checkAccessToken(
        @Header("Authorization") accessToken: String?
    ): AutoLoginDto?

    @POST("members/new-token")
    suspend fun getNewAccessToken(
        @QueryMap refreshToken: Map<String, String?>
    ): NewTokenDto?
}