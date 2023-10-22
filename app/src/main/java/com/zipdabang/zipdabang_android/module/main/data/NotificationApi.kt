package com.zipdabang.zipdabang_android.module.main.data

import com.zipdabang.zipdabang_android.common.ResponseBody
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.Path

interface NotificationApi {

    @DELETE("push-alarm/{alarmId}")
    fun deleteNotification(
        @Header("Authorization") accessToken: String,
        @Path("alarmId") alarmInt: Int
    ): ResponseBody<DeletedTime?>

}