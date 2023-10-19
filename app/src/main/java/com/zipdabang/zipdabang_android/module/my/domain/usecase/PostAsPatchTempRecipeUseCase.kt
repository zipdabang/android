package com.zipdabang.zipdabang_android.module.my.domain.usecase

import android.util.Log
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.common.ResponseCode
import com.zipdabang.zipdabang_android.common.getErrorCode
import com.zipdabang.zipdabang_android.module.my.data.remote.recipewrite.RecipeWriteResponse
import com.zipdabang.zipdabang_android.module.my.domain.repository.MyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.CancellationException
import javax.inject.Inject

class PostAsPatchTempRecipeUseCase @Inject constructor(
    private val repository: MyRepository
) {
    operator fun invoke(
        accessToken: String,
        tempId: Int,
        content: RequestBody,
        thumbnail: MultipartBody.Part?,
        stepImages: List<MultipartBody.Part>?
    ): Flow<Resource<RecipeWriteResponse>> = flow {
        try {
            emit(Resource.Loading())
            val result = repository.postTempRecipeToTemp(accessToken, tempId, content, thumbnail, stepImages)

            when (result.code) {
                ResponseCode.RESPONSE_DEFAULT.code -> {
                    emit(
                        Resource.Success(
                            data = result,
                            code = result.code,
                            message = result.message
                        )
                    )
                    Log.e("POST_TEMPRECIPE", "success")
                }

                else -> {
                    emit(
                        Resource.Error(
                            message = result.message,
                            data = result,
                            code = result.code
                        )
                    )
                    Log.e("POST_TEMPRECIPE", "success but not good")
                }
            }
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()
            val errorCode = errorBody?.getErrorCode()
            Log.e("POST_TEMPRECIPE_NULL", "${errorBody?.string()} ${errorCode}")
            errorCode?.let {
                Log.e("POST_TEMPRECIPE_NOT_NULL", "${errorBody?.string()} ${errorCode}")
                emit(Resource.Error(message = ResponseCode.getMessageByCode(errorCode)))
                return@flow
            }
            emit(Resource.Error(message = e.message ?: "unexpected http error"))
        } catch (e: IOException) {
            Log.e("POST_TEMPRECIPE", "error IOException")
            emit(Resource.Error(message = e.message ?: "unexpected io error"))
        } catch (e: Exception) {
            if (e is CancellationException) {
                throw e
            }
            Log.e("POST_TEMPRECIPE", "error Exception")
            emit(Resource.Error(message = e.message ?: "unexpected error"))
        }
    }
}