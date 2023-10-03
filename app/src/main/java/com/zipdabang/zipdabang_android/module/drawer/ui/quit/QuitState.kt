package com.zipdabang.zipdabang_android.module.drawer.ui.quit

import com.zipdabang.zipdabang_android.module.home.data.banner.BannerDto

data class QuitState(
    val isLoading: Boolean = false,
    val isSuccessful : Boolean = false,
    val isError : Boolean = false,
    val error: String = ""
)
