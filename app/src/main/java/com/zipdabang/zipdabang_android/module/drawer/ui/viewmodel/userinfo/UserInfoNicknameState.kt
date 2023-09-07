package com.zipdabang.zipdabang_android.module.drawer.ui.viewmodel.userinfo

data class UserInfoNicknameState(
    val isLoading : Boolean = false,

    val nickname : String = "",
    val isTried : Boolean = false,
    val isError : Boolean = false,
    val isSuccess : Boolean = false,
    val errorMessage : String = "",
    val successMessage : String = "",
    val btnEnabled : Boolean = false,

    val error : String = ""
)
