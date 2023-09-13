package com.zipdabang.zipdabang_android.module.my.data.remote

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface MyApi {
    @POST("members/logout")
    suspend fun signOut(
        @Header("Authorization") accessToken: String,
        @Body tokensForSignOut: SignOutTokens
    ): SignOutResponseDto
}