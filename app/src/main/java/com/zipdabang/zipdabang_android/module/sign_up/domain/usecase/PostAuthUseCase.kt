package com.zipdabang.zipdabang_android.module.sign_up.domain.usecase

import android.util.Log
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.common.ResponseCode
import com.zipdabang.zipdabang_android.common.getErrorCode
import com.zipdabang.zipdabang_android.module.drawer.domain.repository.DrawerRepository
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.AuthRequest
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.AuthResponse
import com.zipdabang.zipdabang_android.module.sign_up.domain.repository.SignUpRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.CancellationException
import javax.inject.Inject

class PostAuthUseCase @Inject constructor(
    private val repository: SignUpRepository,
    private val repositoryDrawer : DrawerRepository,
) {
    operator fun invoke(authRequest : AuthRequest) : Flow<Resource<AuthResponse>> = flow {
        try {
            emit(Resource.Loading())
            val resultSignup = repository.postPhoneAuth(authRequest = authRequest)
            val resultDrawer = repositoryDrawer.postPhoneAuth(authRequest = authRequest)

            when(resultSignup.code) {
                ResponseCode.RESPONSE_DEFAULT.code ->{
                    emit(
                        Resource.Success(
                            data = resultSignup,
                            code = resultSignup.code,
                            message = resultSignup.message,
                        )
                    )
                }
                else ->{
                    emit(Resource.Error(
                        message = resultSignup.message
                    ))
                }
            }

            when(resultDrawer.code) {
                ResponseCode.RESPONSE_DEFAULT.code ->{
                    emit(
                        Resource.Success(
                            data = resultDrawer,
                            code = resultDrawer.code,
                            message = resultDrawer.message,
                        )
                    )
                }
                else ->{
                    emit(Resource.Error(
                        message = resultDrawer.message
                    ))
                }
            }


        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()
            Log.e("SIGNUP_POST_AUTH", errorBody?.string() ?: "error body is null")
            val errorCode = errorBody?.getErrorCode()
            errorCode?.let {
                emit(Resource.Error(message = ResponseCode.getMessageByCode(errorCode)))
                return@flow
            }
            emit(Resource.Error(message = e.message ?: "unexpected http error"))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.message ?: "unexpected io error"))
        } catch (e: Exception) {
            if (e is CancellationException) {
                throw e
            }
            emit(Resource.Error(message = e.message ?: "unexpected error"))
        }
    }
}