package com.zipdabang.zipdabang_android.module.login.domain

import com.zipdabang.zipdabang_android.module.login.data.member.AuthBody
import com.zipdabang.zipdabang_android.module.login.data.member.AuthDto
import com.zipdabang.zipdabang_android.module.login.data.temp.TempLoginDto

interface AuthRepository {
    suspend fun getAuthResult(body: AuthBody, platform: String): AuthDto

    suspend fun getTempLoginResult(): TempLoginDto
}