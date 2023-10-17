package com.zipdabang.zipdabang_android.module.drawer.data.remote.noticedto

data class NoticeDto(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: NoticeResult
)