package com.zipdabang.zipdabang_android.module.login.data

import com.zipdabang.zipdabang_android.module.login.domain.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi
) : AuthRepository {
    override suspend fun getAuthResult(body: AuthBody, platform: String): AuthDto {
        return api.getAuthResult(body, platform)
    }
}