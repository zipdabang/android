package com.zipdabang.zipdabang_android.module.login.use_case

import android.util.Log
import androidx.datastore.core.DataStore
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.common.ResponseCode
import com.zipdabang.zipdabang_android.common.getErrorCode
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.comment.use_case.BlockUserUseCase
import com.zipdabang.zipdabang_android.module.login.domain.AuthRepository
import com.zipdabang.zipdabang_android.module.login.domain.TempToken
import com.zipdabang.zipdabang_android.module.login.mapper.toTempToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetTempLoginResultUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    companion object {
        const val TAG = "GetTempLoginResultUseCase"
    }
    operator fun invoke(): Flow<Resource<TempToken>> = flow {
        try {
            emit(Resource.Loading())
            val tempLoginResult = authRepository.getTempLoginResult().toTempToken()

            if (tempLoginResult.accessToken != null) {
                when (tempLoginResult.code) {
                    ResponseCode.RESPONSE_DEFAULT.code -> {
                        emit(Resource.Success(
                            data = tempLoginResult,
                            code = tempLoginResult.code,
                            message = tempLoginResult.message
                        ))
                    }
                    else -> {
                        emit(Resource.Error(
                            data = tempLoginResult,
                            message = tempLoginResult.message
                        ))
                    }
                }

            } else {
                emit(Resource.Error(message = tempLoginResult.message))
            }

        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()
            val errorCode = errorBody?.getErrorCode()
            errorCode?.let {
                emit(Resource.Error(message = ResponseCode.getMessageByCode(errorCode)))
                return@flow
            }
            emit(Resource.Error(message = e.message ?: "unexpected http exception"))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.message ?: "unexpected io exception"))

        }
    }
}