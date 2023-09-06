package com.zipdabang.zipdabang_android.module.drawer.ui.viewmodel.userinfostate

import com.zipdabang.zipdabang_android.module.sign_up.ui.viewmodel.UserInfoFormEvent

sealed class UserInfoBasicEvent{
    data class NameChanged(val name : String) : UserInfoBasicEvent()
    data class BirthdayChanged(val birthday : String) : UserInfoBasicEvent()
    data class GenderChanged(val gender : String) : UserInfoBasicEvent()
    data class PhoneNumberChanged(val phoneNumber : String) : UserInfoBasicEvent()
    data class PhoneNumberClicked(val clicked : Boolean) : UserInfoBasicEvent()
    data class AuthNumberChanged(val authNumber : String) : UserInfoBasicEvent()
    data class AuthNumberClicked(val clicked : Boolean) : UserInfoBasicEvent()
    data class BtnEnabled(val enabled: Boolean) : UserInfoBasicEvent()
    data class ValidateChanged(val validate : Boolean) : UserInfoBasicEvent()
}
