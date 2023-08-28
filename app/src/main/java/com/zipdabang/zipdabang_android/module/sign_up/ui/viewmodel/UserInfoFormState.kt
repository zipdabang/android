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
    val phoneNumberCorrectMessage : String = "",
    val phoneNumberIsError : Boolean = false,
    val phoneNumberIsCorrect : Boolean = false,
    val phoneNumberIsTried : Boolean = false,

    val timer : String = "00:00",

    val authNumber : String = "",
    val authNumberErrorMessage : String = "",
    val authNumberCorrectMessage : String = "",
    val authNumberIsError : Boolean = false,
    val authNumberIsCorrect : Boolean = false,
    val authNumberIsTried : Boolean = false,

    val btnEnabled : Boolean = true,
    val validate : Boolean = true,

    val error : String = ""
)
