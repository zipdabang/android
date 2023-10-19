package com.zipdabang.zipdabang_android.module.main.use_case

import android.util.Log
import androidx.datastore.core.DataStore
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.main.domain.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class DeleteNotificationUseCase @Inject constructor(
    private val tokenDataStore: DataStore<Token>,
    private val repository: NotificationRepository
) {
    operator fun invoke(alarmId: Int): Flow<Boolean> = flow {
        try {
            val accessToken = "Bearer ${tokenDataStore.data.first().accessToken}"
            val result = repository.deleteNotification(
                accessToken = accessToken,
                alarmId = alarmId
            )

            val deletedAt = result.result?.deletedAt

            deletedAt?.let {
                emit(true)
                return@flow
            }

            emit(false)

        } catch (e: HttpException) {
            Log.e(TAG, e.localizedMessage ?: "unexpected http exception")
            emit(false)
        } catch (e: IOException) {
            Log.e(TAG, e.localizedMessage ?: "unexpected io exception")
            emit(false)
        } catch (e: Exception) {
            Log.e(TAG, e.localizedMessage ?: "unexpected exception")
            emit(false)
        }
    }

    companion object {
        const val TAG = "DeleteNotificationUseCase"
    }
}
