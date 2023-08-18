package com.zipdabang.zipdabang_android.module.sign_up.data.remote.dto

import retrofit2.http.GET
import retrofit2.http.POST

interface SignUpApi {
    @GET()
    suspend fun getTerms() //:
    @POST()
    suspend fun postPhoneSms() //:
    @POST()
    suspend fun postPhoneAuth() //:
    //주소 api
    @POST()
    suspend fun postNickname() //:
    @GET
    suspend fun getPreferences() //:
    @POST()
    suspend fun postUserInfo() //:

}