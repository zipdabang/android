package com.zipdabang.zipdabang_android.module.drawer.data.remote

import com.zipdabang.zipdabang_android.module.sign_up.data.remote.AuthRequest
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.AuthResponse
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.NicknameResponse
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.PhoneRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface DrawerApi {
    @GET("myInfo")
    suspend fun getUserInfo(
        @Header("Authorization") accessToken: String,
    ) : UserInfoResponse

    @POST("members/phone/sms")
    suspend fun postPhoneSms(
        @Body phoneRequest : PhoneRequest
    ) : AuthResponse
    @POST("members/phone/auth")
    suspend fun postPhoneAuth(
        @Body authRequest : AuthRequest
    ) : AuthResponse
    @GET("members/exist-nickname")
    suspend fun getNickname(
        @Query("nickname") nickname : String
    ) : NicknameResponse
}