package com.zipdabang.zipdabang_android.core.data_store

import kotlinx.coroutines.flow.Flow

interface ProtoRepository {

    val tokens: Flow<Token>

    suspend fun updatePlatform(platform: CurrentPlatform)

    suspend fun updateAccessToken(accessToken: String)

    suspend fun updateRefreshToken(refreshToken: String)

    suspend fun updateFcmToken(fcmToken: String)

    suspend fun resetToken()
}