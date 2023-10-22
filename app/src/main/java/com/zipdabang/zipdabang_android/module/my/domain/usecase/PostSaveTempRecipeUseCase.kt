package com.zipdabang.zipdabang_android.module.my.domain.usecase

import android.util.Log
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.common.ResponseCode
import com.zipdabang.zipdabang_android.common.getErrorCode
import com.zipdabang.zipdabang_android.module.my.data.remote.recipewrite.PostSaveRecipeRequest
import com.zipdabang.zipdabang_android.module.my.data.remote.recipewrite.RecipeWriteResponse
import com.zipdabang.zipdabang_android.module.my.domain.repository.MyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.CancellationException
import javax.inject.Inject

class PostSaveTempRecipeUseCase @Inject constructor(
    private val repository: MyRepository
){
    operator fun invoke(accessToken : String, tempId : Int, categoryId : PostSaveRecipeRequest) : Flow<Resource<RecipeWriteResponse>> = flow{
        try{
            emit(Resource.Loading())
            val result = repository.postTempRecipeSave(accessToken, tempId, categoryId)

            when(result.code) {
                ResponseCode.RESPONSE_DEFAULT.code ->{
                    emit(Resource.Success(
                        data = result,
                        code = result.code,
                        message = result.message
                    ))
                    Log.e("recipewrite-post-save 유즈케이스",  "success")
                }
                else ->{
                    emit(Resource.Error(
                        message = result.message,
                        data = result,
                        code = result.code
                    ))
                    Log.e("recipewrite-post-save 유즈케이스",  "success but not good")
                }
            }
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()
            val errorCode = errorBody?.getErrorCode()
            Log.e("recipewrite-post-save-null 유즈케이스", "${e.response()}")
            errorCode?.let {
                Log.e("recipewrite-post-save-not-null 유즈케이스", "${errorCode}")
                emit(Resource.Error(message = ResponseCode.getMessageByCode(errorCode)))
                return@flow
            }
            emit(Resource.Error(message = e.message ?: "unexpected http error"))
        } catch (e: IOException) {
            Log.e("recipewrite-post-save 유즈케이스",  "error IOException")
            emit(Resource.Error(message = e.message ?: "unexpected io error"))
        } catch (e: Exception){
            if (e is CancellationException){
                throw e
            }
            Log.e("recipewrite-post-save 유즈케이스",  "error Exception")
            emit(Resource.Error(message = e.message ?: "unexpected error"))
        }
    }

}