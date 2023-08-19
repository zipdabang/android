package com.zipdabang.zipdabang_android.module.sign_up.data.remote

import retrofit2.http.GET
import retrofit2.http.POST

interface SignUpApi {
    @GET("members/terms")
    suspend fun getTerms() : TermsDto
    @POST()
    suspend fun postPhoneSms() //:
    @POST()
    suspend fun postPhoneAuth() //:
    //주소 api
    @POST()
    suspend fun postNickname() //:
    @GET("/categories")
    suspend fun getBeverages() : BeveragesDto
    @POST()
    suspend fun postUserInfo() //:

}