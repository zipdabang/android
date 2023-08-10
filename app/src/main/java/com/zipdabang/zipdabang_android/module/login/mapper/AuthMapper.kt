package com.zipdabang.zipdabang_android.module.login.mapper

import com.zipdabang.zipdabang_android.module.login.data.AuthDto
import com.zipdabang.zipdabang_android.module.login.domain.Auth

fun AuthDto.toAuth(): Auth {
    return Auth(
        code = code,
        token = result.accessToken
    )
}