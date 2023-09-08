package com.zipdabang.zipdabang_android.module.splash.data

import com.zipdabang.zipdabang_android.module.splash.domain.AutoLoginApi
import com.zipdabang.zipdabang_android.module.splash.domain.AutoLoginRepository
import javax.inject.Inject

class AutoLoginRepositoryImpl @Inject constructor(
    private val api: AutoLoginApi
): AutoLoginRepository {
    override suspend fun checkAccessToken(accessToken: String?): AutoLoginDto? {
        return api.checkAccessToken(accessToken)
    }

    override suspend fun getNewAccessToken(refreshToken: String?): NewTokenDto? {
        return api.getNewAccessToken(mapOf("refreshToken" to refreshToken))
    }
}