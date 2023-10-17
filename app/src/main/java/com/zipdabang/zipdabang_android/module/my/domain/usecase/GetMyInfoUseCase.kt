package com.zipdabang.zipdabang_android.module.my.domain.usecase

import android.util.Log
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.common.ResponseCode
import com.zipdabang.zipdabang_android.common.getErrorCode
import com.zipdabang.zipdabang_android.module.my.data.remote.myinfo.MyInfoResponse
import com.zipdabang.zipdabang_android.module.my.domain.repository.MyRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.CancellationException
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMyInfoUseCase @Inject constructor(
    private val repository: MyRepository
) {
    operator fun invoke(accessToken : String) : Flow<Resource<MyInfoResponse>> = flow {
        try{
            emit(Resource.Loading())
            val result = repository.getMyInfo(accessToken)
            when(result.code) {
                ResponseCode.RESPONSE_DEFAULT.code->{
                    emit(Resource.Success(
                        data = result,
                        code = result.code,
                        message = result.message
                    ))
                    Log.e("MY_RECIPEWRITE_POST",  "success")
                }
                else ->{
                    emit(Resource.Error(
                        message = result.message,
                        data = result,
                        code = result.code
                    ))
                    Log.e("MY_MYINFO_GET",  "success but not good")
                }
            }

        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()
            val errorCode = errorBody?.getErrorCode()
            Log.e("MY_MYINFO_GET_NULL", "${errorBody?.string()} ${errorCode}")
            errorCode?.let {
                Log.e("MY_MYINFO_GET_NOT_NULL", "${errorBody?.string()} ${errorCode}")
                emit(Resource.Error(message = ResponseCode.getMessageByCode(errorCode)))
                return@flow
            }
            emit(Resource.Error(message = e.message ?: "unexpected http error"))
        } catch (e: IOException) {
            Log.e("MY_MYINFO_GET",  "error IOException")
            emit(Resource.Error(message = e.message ?: "unexpected io error"))
        } catch (e: Exception){
            if (e is CancellationException){
                throw e
            }
            Log.e("MY_MYINFO_GET",  "error Exception")
            emit(Resource.Error(message = e.message ?: "unexpected error"))
        }
    }
}