package com.zipdabang.zipdabang_android.core.data_store

import android.content.Context
import androidx.datastore.core.DataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProtoRepositoryImpl @Inject constructor(
    @ApplicationContext private val applicationContext: Context,
    private val protoDataStore: DataStore<Token>
): ProtoRepository {

    override val tokens: Flow<Token> = protoDataStore.data

    override suspend fun updatePlatformToken(platform: CurrentPlatform, platformToken: String) {
        protoDataStore.updateData { token ->
            // preferences.toBuilder().setShowCompleted(completed).build()
            token.copy(
                platformStatus = platform,
                platformToken = platformToken
            )
        }
    }

    override suspend fun updateAccessToken(accessToken: String) {
        protoDataStore.updateData { token ->
            token.copy(
                accessToken = accessToken
            )
        }
    }

    override suspend fun updateRefreshToken(refreshToken: String) {
        protoDataStore.updateData { token ->
            token.copy(
                refreshToken = refreshToken
            )
        }
    }

    override suspend fun updateFcmToken(fcmToken: String) {
        protoDataStore.updateData { token ->
            token.copy(
                fcmToken = fcmToken
            )
        }
    }

    override suspend fun resetToken() {
        protoDataStore.updateData { token ->
            token.copy(
                accessToken = null,
                refreshToken = null,
                platformStatus = CurrentPlatform.NONE,
                platformToken = null,
                fcmToken = null
            )
        }
    }
}

// https://velog.io/@i_meant_to_be/Proto-With-Hilt-Jetpack-Compose
/*
private val Context.dataStore by dataStore(
    fileName = Constants.DATA_STORE_FILE_NAME,
    serializer = ProtoSerializer
)*/
