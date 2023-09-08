package com.zipdabang.zipdabang_android.module.drawer.ui.viewmodel.userinfo

sealed class UserInfoNicknameEvent{
    data class NicknameChanged(val nickname : String) : UserInfoNicknameEvent()
    data class NicknameClicked(val clicked : Boolean) : UserInfoNicknameEvent()
    data class BtnEnabled(val clicked : Boolean) : UserInfoNicknameEvent()
}