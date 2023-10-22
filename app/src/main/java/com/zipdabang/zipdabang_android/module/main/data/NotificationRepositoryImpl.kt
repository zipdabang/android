package com.zipdabang.zipdabang_android.module.main.data

import com.zipdabang.zipdabang_android.common.ResponseBody
import com.zipdabang.zipdabang_android.module.main.domain.NotificationRepository

class NotificationRepositoryImpl(
    private val api: NotificationApi
): NotificationRepository {
    override suspend fun deleteNotification(
        accessToken: String,
        alarmId: Int
    ): ResponseBody<DeletedTime?> {
        return api.deleteNotification(accessToken, alarmId)
    }
}