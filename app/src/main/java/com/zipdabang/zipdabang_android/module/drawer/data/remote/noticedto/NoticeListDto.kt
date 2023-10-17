package com.zipdabang.zipdabang_android.module.drawer.data.remote.noticedto

data class NoticeListDto(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: NoticeResult?
)