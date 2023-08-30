package com.zipdabang.zipdabang_android.module.sign_up.data.remote

data class InfoRequest(
    val address: String,
    val agreeTermsIdList: List<Int>,
    val birth: String,
    val detailAddress: String,
    val email: String,
    val gender: String,
    val name: String,
    val nickname: String,
    val phoneNum: String,
    val preferBeverages: List<Int>,
    val profileUrl: String,
    val zipCode: String
)