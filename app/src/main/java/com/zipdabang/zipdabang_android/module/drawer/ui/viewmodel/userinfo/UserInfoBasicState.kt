package com.zipdabang.zipdabang_android.module.drawer.ui.viewmodel.userinfo

data class UserInfoBasicState(
    val isLoading : Boolean = false,

    val name : String = "",

    val birthday : String = "",
    val birthdayErrorMessage : String = "",
    val birthdayIsError : Boolean = false,
    val birthdayIsTried : Boolean = false,

    val gender : String = "여",

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

    val btnEnabled : Boolean = false,
    val validate : Boolean = false,

    val error : String = ""
)
