package com.zipdabang.zipdabang_android.module.drawer.data.remote.quitdto

data class QuitRequest(
    val deregisterTypes: List<String>,
    val feedback: String
)