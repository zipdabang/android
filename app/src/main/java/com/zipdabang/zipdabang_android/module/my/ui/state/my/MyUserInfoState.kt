package com.zipdabang.zipdabang_android.module.my.ui.state.my

import androidx.compose.ui.res.stringResource
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.my.data.remote.myinfo.MemberPreferCategoryDto

data class MyUserInfoState(
    val isLoading : Boolean = false,

    val oneline: String = "",
    val followerCount: Int = 0,
    val followingCount: Int = 0,
    val profileUrl: String = "",
    val memberPreferCategoryDto: MemberPreferCategoryDto,
    val nickname: String = "",

    val error : String = ""
)

