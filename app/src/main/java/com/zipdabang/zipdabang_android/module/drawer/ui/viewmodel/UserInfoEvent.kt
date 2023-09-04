package com.zipdabang.zipdabang_android.module.drawer.ui.viewmodel

import com.zipdabang.zipdabang_android.module.sign_up.ui.viewmodel.NicknameFormEvent
import com.zipdabang.zipdabang_android.module.sign_up.ui.viewmodel.UserInfoFormEvent

sealed class UserInfoEvent{
    data class NameChanged(val name : String) : UserInfoEvent()
    data class BirthdayChanged(val birthday : String) : UserInfoEvent()
    data class GenderChanged(val gender : String) : UserInfoEvent()
    data class PhoneNumberChanged(val phoneNumber : String) : UserInfoEvent()
    data class PhoneNumberClicked(val clicked : Boolean) : UserInfoEvent()
    data class AuthNumberChanged(val authNumber : String) : UserInfoEvent()
    data class AuthNumberClicked(val clicked : Boolean) : UserInfoEvent()
    data class NicknameChanged(val nickname : String) : UserInfoEvent()
    data class NicknameCliked(val clicked : Boolean) : UserInfoEvent()
}
