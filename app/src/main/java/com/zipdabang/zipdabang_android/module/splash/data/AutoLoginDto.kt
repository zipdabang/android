package com.zipdabang.zipdabang_android.module.splash.data

import kotlinx.serialization.Serializable

@Serializable
data class AutoLoginDto(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: Unit?
)

