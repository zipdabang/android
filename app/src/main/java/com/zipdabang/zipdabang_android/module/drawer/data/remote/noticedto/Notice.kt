package com.zipdabang.zipdabang_android.module.drawer.data.remote.noticedto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zipdabang.zipdabang_android.common.Constants

data class Notice(
    val createdAt: String,
    val noticeId: Int,
    val title: String
)

