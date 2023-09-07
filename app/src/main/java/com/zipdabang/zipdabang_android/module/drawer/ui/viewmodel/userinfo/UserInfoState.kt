package com.zipdabang.zipdabang_android.module.drawer.ui.viewmodel.userinfo

data class UserInfoState(
    val isLoading : Boolean = false,

    val email : String = "",
    val profileUrl : String = "",
    val name : String = "",
    val birthday : String = "",
    val gender : String ="남",
    val phoneNumber : String = "",
    val nickname : String = "",
    val zipcode : String = "우편번호",
    val address : String = "주소",
    val detailAddress : String = "상세 주소 (배달 주문 시 입력 가능)",

    val error : String = ""
)
