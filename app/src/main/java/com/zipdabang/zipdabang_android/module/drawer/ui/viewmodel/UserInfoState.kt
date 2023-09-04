package com.zipdabang.zipdabang_android.module.drawer.ui.viewmodel

data class UserInfoState(
    val isLoading : Boolean = false,

    val email : String = "",
    val profileUrl : String = "",
    val preferBeverageList : List<Int> = emptyList(),
    val name : String = "",
    val birthday : String = "",
    val gender : String ="남",
    val phoneNumber : String = "",
    val nickname : String = "",

    val error : String = ""
)
