package com.zipdabang.zipdabang_android.module.drawer.ui.state

import com.zipdabang.zipdabang_android.core.navigation.DrawerScreen
import com.zipdabang.zipdabang_android.module.drawer.data.remote.noticedto.Notice

data class NoticeState(
    val isSuccess : Boolean = false,
    val isLoading : Boolean = false,
    val isError : Boolean = false,
    val noticeList : List<Notice> = emptyList(),
    val error : String = ""
)
