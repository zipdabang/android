package com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.following

data class FollowingDto(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: FollowingResult
)