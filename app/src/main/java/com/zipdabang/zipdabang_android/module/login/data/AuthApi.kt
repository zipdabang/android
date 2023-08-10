package com.zipdabang.zipdabang_android.module.login.data

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApi {
    @POST("members/oauth")
    suspend fun getAuthResult(
        @Body authBody: AuthBody,
        @Query("type") type: String
    ): AuthDto
}