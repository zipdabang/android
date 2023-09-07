package com.zipdabang.zipdabang_android.module.drawer.ui.viewmodel.userinfo

data class UserInfoDetailState(
    val isLoading : Boolean = false,

    val zipCode : String = "",
    val zipCodeIsTried : Boolean = false,
    val zipCodeIsError : Boolean = false,
    val zipCodeIsCorrect : Boolean = true,
    val zipCodeErrorMessage : String = "",
    val zipCodeCorrectMessage : String= "",

    val address : String = "",
    val detailAddress : String = "",

    val btnEnabled : Boolean = false,

    val error : String = ""
)
