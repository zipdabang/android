package com.zipdabang.zipdabang_android.module.login.mapper

import com.zipdabang.zipdabang_android.module.login.data.AuthDto
import com.zipdabang.zipdabang_android.module.login.domain.Auth
import com.zipdabang.zipdabang_android.module.login.domain.ZipdabangToken

fun AuthDto.toAuth(): Auth {
    return Auth(
        code = code,
        zipdabangToken = result?.let {
            ZipdabangToken(
                accessToken = it.accessToken,
                refreshToken = it.refreshToken
            )
        } ?: null
    )
}