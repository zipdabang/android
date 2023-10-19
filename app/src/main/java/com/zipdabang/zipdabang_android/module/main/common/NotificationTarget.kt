package com.zipdabang.zipdabang_android.module.main.common

import com.zipdabang.zipdabang_android.module.main.data.NotificationApi

sealed class NotificationTarget(val target: String) {
    object Recipe: NotificationTarget("RECIPE")
    object User: NotificationTarget("USER")
    object MyPage: NotificationTarget("MYPAGE")
    object Notification: NotificationTarget("NOTIFICATION")
}
