package com.zipdabang.zipdabang_android.module.drawer.data.remote

data class UserInfoResult(
    val agreeTermsIdList: List<Int>,
    val birth: String,
    val email: String,
    val gender: String,
    val name: String,
    val nickname: String,
    val phoneNum: String,
    val preferBeverages: List<Int>,
    val profileUrl: String
)