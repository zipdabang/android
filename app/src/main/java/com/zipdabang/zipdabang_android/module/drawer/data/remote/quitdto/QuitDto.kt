package com.zipdabang.zipdabang_android.module.drawer.data.remote.quitdto

data class QuitDto(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: QuitResult
)