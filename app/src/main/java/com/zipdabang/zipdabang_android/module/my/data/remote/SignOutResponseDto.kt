package com.zipdabang.zipdabang_android.module.my.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class SignOutResponseDto(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: SignOutResult?
)