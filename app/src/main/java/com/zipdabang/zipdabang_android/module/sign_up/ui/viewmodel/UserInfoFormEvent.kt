package com.zipdabang.zipdabang_android.module.sign_up.ui.viewmodel

sealed class UserInfoFormEvent{
    data class NameChanged(val name : String) : UserInfoFormEvent()
    data class BirthdayChanged(val birthday : String) : UserInfoFormEvent()
    data class GenderChanged(val gender : String) : UserInfoFormEvent()
    data class PhoneNumberChanged(val phoneNumber : String) : UserInfoFormEvent()
    data class PhoneNumberClicked(val clicked : Boolean) : UserInfoFormEvent()
    data class AuthNumberChanged(val authNumber : String) : UserInfoFormEvent()
    data class AuthNumberClicked(val clicked : Boolean) : UserInfoFormEvent()
    data class BtnChanged(val enabled: Boolean) : UserInfoFormEvent()
    data class ValidateChanged(val validate : Boolean) : UserInfoFormEvent()

}
