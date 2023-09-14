package com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfodto

data class UserPreferencesResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: UserPreferencesResult
)