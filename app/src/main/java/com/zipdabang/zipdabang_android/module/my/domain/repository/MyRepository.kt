package com.zipdabang.zipdabang_android.module.my.domain.repository

import com.zipdabang.zipdabang_android.module.my.data.remote.SignOutResponseDto
import com.zipdabang.zipdabang_android.module.my.data.remote.SignOutTokens

interface MyRepository {
    suspend fun signOut(accessToken: String, signOutTokens: SignOutTokens): SignOutResponseDto
}