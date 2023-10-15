package com.zipdabang.zipdabang_android.module.my.domain.usecase

import android.util.Log
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.common.ResponseCode
import com.zipdabang.zipdabang_android.common.getErrorCode
import com.zipdabang.zipdabang_android.module.my.data.remote.recipewrite.RecipeWriteBeverageResult
import com.zipdabang.zipdabang_android.module.my.domain.repository.MyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.CancellationException
import javax.inject.Inject

class GetRecipeWriteBeveragesUseCase @Inject constructor(
    private val repository : MyRepository
) {
    operator fun invoke(accessToken : String) : Flow<Resource<RecipeWriteBeverageResult?>> = flow{
        try{
            emit(Resource.Loading())
            val result = repository.getRecipeWriteBeverages(accessToken = accessToken)
            when(result.code){
                ResponseCode.RESPONSE_DEFAULT.code ->{
                    emit(Resource.Success(
                        data = result.result,
                        code = result.code,
                        message = result.message,
                    ))
                }
                else ->{
                    emit(Resource.Error(
                        message = result.message
                    ))
                }
            }
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()
            Log.e("RECIPEWRITE_GET_CATEGORIES", errorBody?.string() ?: "error body is null")
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