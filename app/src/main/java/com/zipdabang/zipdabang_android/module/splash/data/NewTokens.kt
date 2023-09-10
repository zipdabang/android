package com.zipdabang.zipdabang_android.module.splash.data

import kotlinx.serialization.Serializable

@Serializable
data class NewTokens(
    val accessToken: String,
    val refreshToken: String
)