package com.zipdabang.zipdabang_android.module.my.data.remote.otherinfo

import com.zipdabang.zipdabang_android.module.my.data.remote.otherinfo.MemberPreferCategoryDto

data class OtherInfoResult(
    val caption: String,
    val checkFollowing: Boolean,
    val checkSelf: Boolean,
    val followerCount: Int,
    val followingCount: Int,
    val imageUrl: String,
    val memberId: Int,
    val memberPreferCategoryDto: MemberPreferCategoryDto,
    val nickname: String
)