package com.zipdabang.zipdabang_android.module.login.domain

import com.zipdabang.zipdabang_android.module.login.data.AuthBody
import com.zipdabang.zipdabang_android.module.login.data.AuthDto

interface AuthRepository {
    suspend fun getAuthResult(body: AuthBody, platform: String): AuthDto
}