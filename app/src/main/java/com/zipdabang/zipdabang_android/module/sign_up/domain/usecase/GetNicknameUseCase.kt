package com.zipdabang.zipdabang_android.module.sign_up.domain.usecase

import android.util.Log
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.common.ResponseCode
import com.zipdabang.zipdabang_android.common.getErrorCode
import com.zipdabang.zipdabang_android.module.drawer.domain.repository.DrawerRepository
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.NicknameResponse
import com.zipdabang.zipdabang_android.module.sign_up.domain.repository.SignUpRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.CancellationException
import javax.inject.Inject

class GetNicknameUseCase @Inject constructor(
    private val repository : SignUpRepository,
    private val repositoryDrawer : DrawerRepository
) {
    operator fun invoke(nickname : String) : Flow<Resource<NicknameResponse>> = flow{
        try{
            emit(Resource.Loading())
            val resultSignup = repository.getNickname(nickname = nickname)
            val resultDrawer = repositoryDrawer.getNickname(nickname = nickname)


            when(resultSignup.code){
                ResponseCode.RESPONSE_DEFAULT.code ->{
                    emit(
                        Resource.Success(
                            data = resultSignup,
                            code = resultSignup.code,
                            message = resultSignup.message
                        )
                    )
                }
                ResponseCode.OAUTH_SIGN_UP_NICKNAME_EXISTS.code ->{
                    emit(
                        Resource.Success(
                            data = resultSignup,
                            code = resultSignup.code,
                            message = resultSignup.message
                        )
                    )
                }
                ResponseCode.OAUTH_SIGN_UP_NICKNAME_AVAILABLE.code ->{
                    emit(
                        Resource.Success(
                            data = resultSignup,
                            code = resultSignup.code,
                            message = resultSignup.message
                        )
                    )
                }
                else ->{
                    emit(Resource.Error(
                        message = resultSignup.message
                    ))
                }
            }
            when(resultDrawer.code){
                ResponseCode.RESPONSE_DEFAULT.code ->{
                    emit(
                        Resource.Success(
                            data = resultDrawer,
                            code = resultDrawer.code,
                            message = resultDrawer.message
                        )
                    )
                }
                ResponseCode.OAUTH_SIGN_UP_NICKNAME_EXISTS.code ->{
                    emit(
                        Resource.Success(
                            data = resultDrawer,
                            code = resultDrawer.code,
                            message = resultDrawer.message
                        )
                    )
                }
                ResponseCode.OAUTH_SIGN_UP_NICKNAME_AVAILABLE.code ->{
                    emit(
                        Resource.Success(
                            data = resultDrawer,
                            code = resultDrawer.code,
                            message = resultDrawer.message
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
            Log.e("SIGNUP_GET_NICKNAME", errorBody?.string() ?: "error body is null")
            val errorCode = errorBody?.getErrorCode()
            errorCode?.let {
                emit(Resource.Error(message = ResponseCode.getMessageByCode(errorCode)))
                return@flow
            }
            emit(Resource.Error(message = e.message ?: "unexpected http error"))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.message ?: "unexpected io error"))
        } catch (e: Exception){
            if (e is CancellationException){
                throw e
            }
            emit(Resource.Error(message = e.message ?: "unexpected error"))
        }
    }
}