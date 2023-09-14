package com.zipdabang.zipdabang_android.common

import kotlinx.serialization.Serializable

@Serializable
data class ResponseBody<T: Any?>(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val result: T
)
