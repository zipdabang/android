package com.zipdabang.zipdabang_android.module.splash.domain

import com.zipdabang.zipdabang_android.module.splash.data.AutoLoginDto
import com.zipdabang.zipdabang_android.module.splash.data.NewTokenDto
import com.zipdabang.zipdabang_android.module.splash.data.RefreshTokenQuery

interface AutoLoginRepository {

    suspend fun checkAccessToken(accessToken: String?): AutoLoginDto?

    suspend fun getNewAccessToken(refreshToken: String?): NewTokenDto?

}