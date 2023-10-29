package com.zipdabang.zipdabang_android.module.drawer.domain.usecase

import android.util.Log
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.common.ResponseCode
import com.zipdabang.zipdabang_android.common.getErrorCode
import com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfo.UserInfoEditResponse
import com.zipdabang.zipdabang_android.module.drawer.domain.repository.DrawerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.CancellationException
import javax.inject.Inject

class PatchUserInfoProfileUseCase @Inject constructor(
    private val repository : DrawerRepository
) {
    operator fun invoke(accessToken : String, userInfoProfile : MultipartBody.Part) : Flow<Resource<UserInfoEditResponse>> = flow{
        try{
            emit(Resource.Loading())
            val result = repository.patchUserInfoProfile(accessToken = accessToken, userInfoProfile = userInfoProfile)

            when(result.code){
                ResponseCode.RESPONSE_DEFAULT.code ->{
                    emit(Resource.Success(
                        data = result,
                        code = result.code,
                        message = result.message
                    ))
                    Log.e("DRAWER_PATCH_USERINFOPROFILE", "Success")
                }
                else ->{
                    emit(Resource.Error(
                        message = result.message
                    ))
                    Log.e("DRAWER_PATCH_USERINFOPROFILE", "Success but not good")
                }
            }
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()
            val errorCode = errorBody?.getErrorCode()

            errorCode?.let {
                emit(Resource.Error(message = ResponseCode.getMessageByCode(errorCode)))
                return@flow
            }
            emit(Resource.Error(message = e.message ?: "unexpected http error"))
        } catch (e: IOException) {
            Log.e("DRAWER_PATCH_USERINFOPROFILE", "IOException")
            emit(Resource.Error(message = e.message ?: "unexpected io error"))
        } catch (e: Exception){
            if (e is CancellationException){
                throw e
            }
            Log.e("DRAWER_PATCH_USERINFOPROFILE", "Exception")
            emit(Resource.Error(message = e.message ?: "unexpected error"))
        }
    }
}