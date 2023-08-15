package com.zipdabang.zipdabang_android.module.sign_up.ui.viewmodel

sealed class RegistrationFormEvent{
    data class TermsChanged(val checked : Boolean) : RegistrationFormEvent()
    data class NameChanged(val name: String) : RegistrationFormEvent()
    data class GenderChanged(val gender : Boolean) : RegistrationFormEvent()
    data class BirthdayChanged(val birthday : Int) : RegistrationFormEvent()
    data class PhonenumberChanged(val phonenumber : Int) : RegistrationFormEvent()
    data class AddressChanged(val address : String) : RegistrationFormEvent()
    data class ZipcodeChanged(val zipcode : Int ) : RegistrationFormEvent()
    data class DetailaddressChanged(val detailaddress : String) : RegistrationFormEvent()
    data class NicknameChanged(val nickname : String) : RegistrationFormEvent()
    //preferencs에 대한 거?
    object SubmitTerms : RegistrationFormEvent()
    object SubmitUserInfo : RegistrationFormEvent()
    object SubmitPreferences : RegistrationFormEvent()
}
