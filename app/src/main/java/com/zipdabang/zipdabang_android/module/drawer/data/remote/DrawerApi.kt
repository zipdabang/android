package com.zipdabang.zipdabang_android.module.drawer.data.remote

import retrofit2.http.GET
import retrofit2.http.Header

interface DrawerApi {
    @GET("myinfo")
    suspend fun getUserInfo(
        @Header("Authorization") accessToken: String,
    ) : UserInfoResponse
}