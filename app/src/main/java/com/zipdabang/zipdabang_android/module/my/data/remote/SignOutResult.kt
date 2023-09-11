package com.zipdabang.zipdabang_android.module.my.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class SignOutResult(
    val calledAt: String,
    val memberId: Int,
    val status: String
)