package com.zipdabang.zipdabang_android.module.drawer.ui.state.userinfo

import com.zipdabang.zipdabang_android.module.sign_up.data.remote.BeverageCategory

data class UserInfoOneLineState (
    val isLoading : Boolean = false,

    val oneline : String = "",
    val isSuccess : Boolean = false,
    val btnEnabled : Boolean = false,

    val error : String = ""
)