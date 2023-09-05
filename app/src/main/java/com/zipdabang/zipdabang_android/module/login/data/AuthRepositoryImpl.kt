package com.zipdabang.zipdabang_android.module.login.data

import com.zipdabang.zipdabang_android.module.login.data.member.AuthBody
import com.zipdabang.zipdabang_android.module.login.data.member.AuthDto
import com.zipdabang.zipdabang_android.module.login.data.temp.TempLoginDto
import com.zipdabang.zipdabang_android.module.login.domain.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi
) : AuthRepository {
    override suspend fun getAuthResult(body: AuthBody, platform: String): AuthDto {
        return api.getAuthResult(body, platform)
    }

    override suspend fun getTempLoginResult(): TempLoginDto {
        return api.tempAuthResult()
    }
}