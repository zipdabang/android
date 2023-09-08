package com.zipdabang.zipdabang_android.module.login.data.temp

import kotlinx.serialization.Serializable

@Serializable
data class TempLoginDto(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: TempTokenDto
)