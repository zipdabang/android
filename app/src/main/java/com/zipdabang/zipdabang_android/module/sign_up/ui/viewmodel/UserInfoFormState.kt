package com.zipdabang.zipdabang_android.module.sign_up.ui.viewmodel

data class UserInfoFormState(
    val isLoading : Boolean = false,

    val name : String = "",
    val birthday : String = "",
    val birthdayErrorMessage : String = "",
    val birthdayIsError : Boolean = false,
    val gender : String ="",
    val phoneNumber : String = "",
    val phoneNumberErrorMessage : String = "",
    val phoneNumberIsError : Boolean = false,
    val authTime : String = "00:00",
    val authNumber : String = "",
    val authNumberErrorMessage : String = "",
    val authNumberIsError : Boolean = false,
    val btnEnabled : Boolean = false,

    val error : String = ""
)
