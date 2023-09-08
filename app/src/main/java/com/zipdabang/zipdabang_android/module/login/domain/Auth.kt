package com.zipdabang.zipdabang_android.module.login.domain

data class Auth(
    val code: Int,
    val zipdabangToken: ZipdabangToken?
)

data class ZipdabangToken(
    val accessToken: String?,
    val refreshToken: String?
)

data class TempToken(
    val code: Int,
    val message: String,
    val accessToken: String?
)