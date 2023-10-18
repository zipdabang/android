package com.zipdabang.zipdabang_android.module.main.domain

import com.zipdabang.zipdabang_android.common.ResponseBody
import com.zipdabang.zipdabang_android.module.main.data.DeletedTime

interface NotificationRepository {
    suspend fun deleteNotification(accessToken: String, alarmId: Int): ResponseBody<DeletedTime?>
}