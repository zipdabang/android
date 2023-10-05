package com.zipdabang.zipdabang_android.module.my.ui.state

data class FollowOrCancel(
    val isSuccess : Boolean = false,
    val isError : Boolean = false,
    val isLoading : Boolean = false,
    val isFollowing : Boolean = false
)
