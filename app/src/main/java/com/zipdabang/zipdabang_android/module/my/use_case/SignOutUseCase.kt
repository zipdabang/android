package com.zipdabang.zipdabang_android.module.my.use_case

import android.util.Log
import androidx.datastore.core.DataStore
import com.zipdabang.zipdabang_android.common.Constants.TOKEN_NULL
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.common.ResponseCode
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.my.data.remote.SignOutTokens
import com.zipdabang.zipdabang_android.module.my.domain.SignOutResponse
import com.zipdabang.zipdabang_android.module.my.domain.repository.MyRepository
import com.zipdabang.zipdabang_android.module.my.util.toSignOutResponse
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val tokenDataStore: DataStore<Token>,
    private val repository: MyRepository
) {
    operator fun invoke(): Flow<Resource<SignOutResponse>> = flow {
        try {
            emit(Resource.Loading())

            val tokens = tokenDataStore.data.first()
            val accessToken = "Bearer ${tokens.accessToken}"
            val refreshToken = tokens.refreshToken ?: TOKEN_NULL
            val fcmToken = tokens.fcmToken ?: TOKEN_NULL
            val deviceNumber = tokens.deviceNumber ?: TOKEN_NULL

            Log.d("logout", "tokens : $tokens")

            val tokensForSignOut = SignOutTokens(
                fcmToken = fcmToken,
                serialNumber = deviceNumber
            )

            Log.d("logout", "${repository.signOut(
                accessToken = accessToken,
                signOutTokens = tokensForSignOut
            )}")

            val result = repository.signOut(
                accessToken = accessToken,
                signOutTokens = tokensForSignOut
            ).toSignOutResponse()

            Log.d("result", result.code.toString())

            when (result.code) {
                ResponseCode.RESPONSE_DEFAULT.code -> {
                    emit(
                        Resource.Success(
                            code = result.code,
                            message = result.message,
                            data = result
                        )
                    )
                }

                else -> {
                    emit(
                        Resource.Error(
                            message = ResponseCode.getMessageByCode(result.code),
                            data = result
                        )
                    )
                }
            }
        } catch (e: HttpException) {
            emit(Resource.Error(e.message ?: "unexpected http exception"))
        } catch (e: IOException) {
            emit(Resource.Error(e.message ?: "unexpected io exception"))
        } catch (e: Exception) {
            if (e is CancellationException) {
                throw e
            } else {
                emit(Resource.Error("unexpected error : ${e.message}"))
            }
        }
    }
}