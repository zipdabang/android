package com.zipdabang.zipdabang_android.module.my.ui.state

data class CommonInfoState(
    val nickName : String = "",
    val followNum : Int = 0,
    val followingNum : Int =0,
    val profileUrl : String = "",
    val isFollowing : Boolean  = false
)
