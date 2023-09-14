package com.zipdabang.zipdabang_android.module.sign_up.ui.state

sealed class NicknameFormEvent{
    data class NicknameChanged(val nickname : String) : NicknameFormEvent()
    data class NicknameCliked(val clicked : Boolean) : NicknameFormEvent()
}
