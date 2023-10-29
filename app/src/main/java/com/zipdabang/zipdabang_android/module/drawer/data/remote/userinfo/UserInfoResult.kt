package com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfo

data class UserInfoResult(
    val email: String,
    val memberBasicInfoDto: MemberBasicInfoDto,
    val memberDetailInfoDto: MemberDetailInfoDto,
    val nickname: String,
    val caption : String,
    val preferCategories: PreferCategories,
    val profileUrl: String
)