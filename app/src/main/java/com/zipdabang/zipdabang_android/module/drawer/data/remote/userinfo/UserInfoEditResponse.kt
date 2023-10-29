package com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfo

data class UserInfoEditResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: UserInfoEditResult?
)