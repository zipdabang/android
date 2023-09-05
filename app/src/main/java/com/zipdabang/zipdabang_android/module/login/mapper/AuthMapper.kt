package com.zipdabang.zipdabang_android.module.login.mapper

import com.zipdabang.zipdabang_android.module.login.data.member.AuthDto
import com.zipdabang.zipdabang_android.module.login.data.temp.TempLoginDto
import com.zipdabang.zipdabang_android.module.login.data.temp.TempTokenDto
import com.zipdabang.zipdabang_android.module.login.domain.Auth
import com.zipdabang.zipdabang_android.module.login.domain.TempToken
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

fun TempLoginDto.toTempToken(): TempToken {
    return TempToken(
        code = code,
        message = message,
        accessToken = result.accessToken
    )
}