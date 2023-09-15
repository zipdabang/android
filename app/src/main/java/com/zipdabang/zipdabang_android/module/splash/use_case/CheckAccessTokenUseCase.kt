package com.zipdabang.zipdabang_android.module.splash.use_case

import android.util.Log
import androidx.datastore.core.DataStore
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.splash.data.AutoLoginDto
import com.zipdabang.zipdabang_android.module.splash.data.AutoLoginRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.CancellationException
import javax.inject.Inject

class CheckAccessTokenUseCase @Inject constructor(
    private val autoLoginRepository: AutoLoginRepositoryImpl,
    private val tokenDataStore: DataStore<Token>
) {

    companion object {
        const val TAG = "CheckAccessTokenUseCase"
    }

    operator fun invoke(): Flow<Resource<AutoLoginDto>> = flow {
        try {
            emit(Resource.Loading())

            val accessToken = "Bearer ${tokenDataStore.data.first().accessToken}"

            Log.d(TAG, "access token : $accessToken")

            val result = autoLoginRepository.checkAccessToken(accessToken = accessToken)

            Log.d(TAG, "check access token validity result : $result")

            result?.let {
                emit(Resource.Success(
                    data = it,
                    code = it.code,
                    message = it.message))
            } ?: run {
                emit(Resource.Error(message = "result is nothing"))
            }
        } catch (e: HttpException) {
            Log.e(TAG, "${e.message} ${e.code()}")
            emit(Resource.Error(message = e.message ?: "unexpected http exception"))
        } catch (e: IOException) {
            Log.e(TAG, e.message ?: "unexpected io error")
            emit(Resource.Error(message = e.message ?: "unexpected io exception"))
        } catch (e: Exception) {
            if (e is CancellationException) {
                throw e
            }
            emit(Resource.Error(message = e.message ?: "unexpected etc exception"))
        }
    }
}