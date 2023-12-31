package com.zipdabang.zipdabang_android.module.sign_up.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SignUpApi {
    @GET("members/terms")
    suspend fun getTerms() : TermsResponse
    @POST("members/phone/sms")
    suspend fun postPhoneSms(
        @Body phoneRequest : PhoneRequest
    ) : AuthResponse
    @POST("members/phone/auth")
    suspend fun postPhoneAuth(
        @Body authRequest : AuthRequest
    ) : AuthResponse
    //주소 api
    @GET("members/exist-nickname")
    suspend fun getNickname(
        @Query("nickname") nickname : String
    ) : NicknameResponse
    @GET("/categories")
    suspend fun getBeverages() : BeveragesResponse
    @POST("members/oauth/info")
    suspend fun postUserInfo(
        @Query("type") social : String,
        @Body infoRequest : InfoRequest
    ) : InfoResponse

}