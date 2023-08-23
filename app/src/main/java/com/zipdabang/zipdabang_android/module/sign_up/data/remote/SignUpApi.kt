package com.zipdabang.zipdabang_android.module.sign_up.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SignUpApi {
    @GET("members/terms")
    suspend fun getTerms() : TermsDto
    @POST("members/phone/sms")
    suspend fun postPhoneSms(
        @Body phoneRequest : PhoneRequest
    ) : PhoneResponse
    @POST()
    suspend fun postPhoneAuth() //:
    //주소 api
    @GET("members/exist-nickname")
    suspend fun getNickname(
        @Query("nickname") nickname : String
    ) : NicknameResponse
    @GET("/categories")
    suspend fun getBeverages() : BeveragesDto
    @POST()
    suspend fun postUserInfo() //:

}