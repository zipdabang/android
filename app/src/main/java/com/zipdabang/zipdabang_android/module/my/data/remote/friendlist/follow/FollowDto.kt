package com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.follow

data class FollowDto(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: FollowResult?
)