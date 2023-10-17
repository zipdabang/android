package com.zipdabang.zipdabang_android.module.my.data.remote.myinfo

data class MyInfoResult(
    val caption: String,
    val checkFollower: Boolean,
    val checkFollowing: Boolean,
    val checkSelf: Boolean,
    val followerCount: Int,
    val followingCount: Int,
    val imageUrl: String,
    val memberId: Int,
    val memberPreferCategoryDto: MemberPreferCategoryDto,
    val nickname: String
)