package com.zipdabang.zipdabang_android.module.drawer.ui.viewmodel

import com.zipdabang.zipdabang_android.module.sign_up.ui.viewmodel.NicknameFormEvent

sealed class UserInfoNicknameEvent{
    data class NicknameChanged(val nickname : String) : UserInfoNicknameEvent()
    data class NicknameCliked(val clicked : Boolean) : UserInfoNicknameEvent()
}
