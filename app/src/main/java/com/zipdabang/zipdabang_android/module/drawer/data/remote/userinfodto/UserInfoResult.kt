package com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfodto

data class UserInfoResult(
    val email: String,
    val memberBasicInfoDto: MemberBasicInfoDto,
    val memberDetailInfoDto: MemberDetailInfoDto,
    val nickname: String,
    val profileUrl: String
)