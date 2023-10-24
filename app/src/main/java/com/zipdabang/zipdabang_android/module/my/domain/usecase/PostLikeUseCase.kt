package com.zipdabang.zipdabang_android.module.my.domain.usecase

import android.util.Log
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.common.ResponseCode
import com.zipdabang.zipdabang_android.common.getErrorCode
import com.zipdabang.zipdabang_android.module.my.data.remote.myrecipes.like.PostLikeResponse
import com.zipdabang.zipdabang_android.module.my.data.remote.myrecipes.like.PostLikeResult
import com.zipdabang.zipdabang_android.module.my.data.remote.recipewrite.RecipeWriteResponse
import com.zipdabang.zipdabang_android.module.my.domain.repository.MyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.CancellationException
import javax.inject.Inject

class PostLikeUseCase  @Inject constructor(
    private val repository: MyRepository
) {
    operator fun invoke(accessToken : String, recipeId : Int) : Flow<Resource<PostLikeResult?>> = flow {
        try {
            emit(Resource.Loading())
            val result = repository.postLike(accessToken, recipeId)

            when(result.code) {
                ResponseCode.RESPONSE_DEFAULT.code ->{
                    emit(Resource.Success(
                        data = result.result,
                        code = result.code,
                        message = result.message
                    ))
                    Log.e("my_like_post 유즈케이스",  "success")
                }
                else ->{
                    emit(Resource.Error(
                        message = result.message,
                        data = result.result,
                        code = result.code
                    ))
                    Log.e("my_like_post 유즈케이스",  "success but not good")
                }
            }
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()
            val errorCode = errorBody?.getErrorCode()
            Log.e("my_like_post-null 유즈케이스", "${e.response()}")
            errorCode?.let {
                Log.e("my_like_post-not_null 유즈케이스", "${errorCode}")
                emit(Resource.Error(message = ResponseCode.getMessageByCode(errorCode)))
                return@flow
            }
            emit(Resource.Error(message = e.message ?: "unexpected http error"))
        } catch (e: IOException) {
            Log.e("my_like_post 유즈케이스",  "error IOException")
            emit(Resource.Error(message = e.message ?: "unexpected io error"))
        } catch (e: Exception){
            if (e is CancellationException){
                throw e
            }
            Log.e("my_like_post 유즈케이스",  "error Exception")
            emit(Resource.Error(message = e.message ?: "unexpected error"))
        }
    }
}