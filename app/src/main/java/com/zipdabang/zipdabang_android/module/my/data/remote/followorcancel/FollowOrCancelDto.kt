package com.zipdabang.zipdabang_android.module.my.data.remote.followorcancel

data class FollowOrCancelDto(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: FollowOrCancelResult
)