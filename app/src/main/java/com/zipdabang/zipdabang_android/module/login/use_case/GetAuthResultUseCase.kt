package com.zipdabang.zipdabang_android.module.login.use_case

import android.util.Log
import androidx.datastore.core.DataStore
import com.zipdabang.zipdabang_android.common.Constants.TOKEN_NULL
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.common.ResponseCode
import com.zipdabang.zipdabang_android.common.getErrorCode
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.comment.use_case.BlockUserUseCase
import com.zipdabang.zipdabang_android.module.login.data.member.AuthBody
import com.zipdabang.zipdabang_android.module.login.domain.Auth
import com.zipdabang.zipdabang_android.module.login.domain.AuthRepository
import com.zipdabang.zipdabang_android.module.login.mapper.toAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.CancellationException
import javax.inject.Inject

class GetAuthResultUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val tokenDataStore: DataStore<Token>
) {
    companion object {
        const val TAG = "GetAuthResultUseCase"
    }

    operator fun invoke(email: String, platform: String): Flow<Resource<Auth>> = flow {
        try {
            emit(Resource.Loading())

            val fcmToken = tokenDataStore.data.first().fcmToken ?: TOKEN_NULL
            val serialNumber = tokenDataStore.data.first().deviceNumber ?: TOKEN_NULL

            val authBody = AuthBody(
                email = email,
                fcmToken = fcmToken,
                serialNumber = serialNumber
            )

            val authResult = repository.getAuthResult(body = authBody, platform = platform).toAuth()
            emit(
                Resource.Success(
                    data = authResult,
                    code = authResult.code,
                    message = ResponseCode.getMessageByCode(authResult.code)
                )
            )
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()
            Log.e(TAG, errorBody?.string() ?: "error body is null")
            val errorCode = errorBody?.getErrorCode()
            errorCode?.let {
                emit(Resource.Error(message = ResponseCode.getMessageByCode(errorCode)))
                return@flow
            }
            emit(Resource.Error(e.localizedMessage ?: "unexpected error"))
        } catch (e: IOException) {
            emit(Resource.Error("couldn't reach server, check internet connection"))
        } catch (e: Exception) {
            if (e is CancellationException) {
                throw e
            }
            emit(Resource.Error(e.message ?: "unexpected error"))
        }

    }
}
