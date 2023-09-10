package com.zipdabang.zipdabang_android.module.drawer.ui.viewmodel.userinfo

import com.zipdabang.zipdabang_android.R

data class UserInfoState(
    val isLoading : Boolean = false,

    val email : String = "",
    val profileUrl : String = R.drawable.img_profile.toString(),
    val name : String = "이름",
    val birthday : String = "생년월일 앞자리",
    val gender : String = "남",
    val phoneNumber : String = "전화번호 (-제외)",
    val nickname : String = "비니",
    val zipcode : String = "우편번호",
    val address : String = "주소",
    val detailAddress : String = "상세 주소 (배달 주문 시 입력 가능)",

    val error : String = ""
)
