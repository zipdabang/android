package com.zipdabang.zipdabang_android.module.drawer.ui.state.userinfo

import com.zipdabang.zipdabang_android.R

data class UserInfoState(
    val isLoading : Boolean = false,

    val email : String = "",
    val profileUrl : String = R.drawable.img_profile.toString(),
    val name : String = "",
    val birthday : String = "",
    val gender : String = "",
    val phoneNumber : String = "",
    val nickname : String = "",
    val preferBeverageList : List<String> = listOf(""),
    val size : Int = 0,
    val preferBeverageCheckList : List<Boolean> = emptyList(),
    val zipcode : String = "",
    val address : String = "",
    val detailAddress : String = "",

    val error : String = ""
)
