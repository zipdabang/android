package com.zipdabang.zipdabang_android.module.login.domain

data class Auth(
    val code: Int,
    val zipdabangToken: ZipdabangToken?
)

data class ZipdabangToken(
    val accessToken: String?,
    val refreshToken: String?
)
