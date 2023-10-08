package com.zipdabang.zipdabang_android.module.drawer.ui.state.userinfo

sealed class UserInfoOneLineEvent {
    data class OneLineChanged(val oneline : String) : UserInfoOneLineEvent()
    data class BtnEnabled(val enabled : Boolean) : UserInfoOneLineEvent()
}