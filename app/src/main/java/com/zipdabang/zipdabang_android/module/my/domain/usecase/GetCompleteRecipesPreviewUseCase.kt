package com.zipdabang.zipdabang_android.module.my.domain.usecase

import android.util.Log
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.common.ResponseCode
import com.zipdabang.zipdabang_android.common.getErrorCode
import com.zipdabang.zipdabang_android.module.my.data.remote.myrecipes.preview.CompleteRecipesWithImgPreviewResponse
import com.zipdabang.zipdabang_android.module.my.data.remote.myrecipes.preview.CompleteRecipesWithImgResult
import com.zipdabang.zipdabang_android.module.my.data.remote.recipeedit.complete.GetCompleteRecipeResult
import com.zipdabang.zipdabang_android.module.my.domain.repository.MyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.CancellationException
import javax.inject.Inject

class GetCompleteRecipesPreviewUseCase @Inject constructor(
    private val repository: MyRepository
) {
    operator fun invoke(accessToken: String) : Flow<Resource<CompleteRecipesWithImgResult?>> = flow {
        try {
            emit(Resource.Loading())
            val result = repository.getMyCompleteRecipesWithImgPreview(accessToken)

            when (result.code){
                ResponseCode.RESPONSE_DEFAULT.code ->{
                    emit(Resource.Success(
                        data = result.result,
                        code = result.code,
                        message = result.message
                    ))
                    Log.e("my_completerecipe_preview 유즈케이스",  "success")
                }
                ResponseCode.RESPONSE_NO_DATA.code ->{
                    emit(Resource.Success(
                        data = result.result,
                        code = result.code,
                        message = result.message
                    ))
                }
                else ->{
                    emit(Resource.Error(
                        message = result.message,
                        data = result.result,
                        code = result.code
                    ))
                    Log.e("my_completerecipe_preview 유즈케이스",  "success but not good")
                }
            }

        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()
            val errorCode = errorBody?.getErrorCode()
            Log.e("my_completerecipe_preview 유즈케이스 null", "${e.response()}")
            errorCode?.let {
                Log.e("my_completerecipe_preview 유즈케이스 not null", "${errorCode}")
                emit(Resource.Error(message = ResponseCode.getMessageByCode(errorCode)))
                return@flow
            }
            emit(Resource.Error(message = e.message ?: "unexpected http error"))
        } catch (e: IOException) {
            Log.e("my_completerecipe_preview 유즈케이스",  "error IOException")
            emit(Resource.Error(message = e.message ?: "unexpected io error"))
        } catch (e: Exception){
            if (e is CancellationException){
                throw e
            }
            Log.e("my_completerecipe_preview 유즈케이스",  "error Exception")
            emit(Resource.Error(message = e.message ?: "unexpected error"))
        }
    }
}