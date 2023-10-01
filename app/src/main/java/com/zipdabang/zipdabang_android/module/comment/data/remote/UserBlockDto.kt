package com.zipdabang.zipdabang_android.module.comment.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class UserBlockDto(
    val calledAt: String,
    val memberId: Int,
    val status: String
)