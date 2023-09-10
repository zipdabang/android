package com.zipdabang.zipdabang_android.module.login.data

import com.zipdabang.zipdabang_android.module.login.data.member.AuthBody
import com.zipdabang.zipdabang_android.module.login.data.member.AuthDto
import com.zipdabang.zipdabang_android.module.login.data.temp.TempLoginDto
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApi {
    @POST("members/oauth")
    suspend fun getAuthResult(
        @Body authBody: AuthBody,
        @Query("type") type: String
    ): AuthDto

    @POST("members/temp-login")
    suspend fun tempAuthResult(): TempLoginDto
}