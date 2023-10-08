package com.zipdabang.zipdabang_android.module.comment.use_case

import android.util.Log
import androidx.datastore.core.DataStore
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.common.ResponseCode
import com.zipdabang.zipdabang_android.common.getErrorCode
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.comment.domain.DeleteResult
import com.zipdabang.zipdabang_android.module.comment.domain.RecipeCommentRepository
import com.zipdabang.zipdabang_android.module.comment.util.toDeleteResult
import com.zipdabang.zipdabang_android.module.recipes.use_case.GetHotRecipesByCategoryUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.CancellationException
import javax.inject.Inject

class DeleteCommentUseCase @Inject constructor(
    private val repository: RecipeCommentRepository,
    private val tokenDataStore: DataStore<Token>
) {

    companion object {
        const val TAG = "DeleteCommentUseCase"
    }

    operator fun invoke(
        recipeId: Int,
        commentId: Int
    ) = flow {
        try {
            emit(Resource.Loading())

            val accessToken = "Bearer ${tokenDataStore.data.first().accessToken}"
            val result = repository.deleteRecipeComment(
                accessToken = accessToken,
                recipeId = recipeId,
                commentId = commentId
            ).toDeleteResult()

            when (result.code) {
                ResponseCode.RESPONSE_DEFAULT.code -> {
                    emit(Resource.Success(data = result, code = result.code, message = result.message))
                }
                else -> {
                    val message = ResponseCode.getMessageByCode(result.code)
                    emit(Resource.Error(message = message))
                }
            }

        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()
            Log.e(TAG, errorBody?.string() ?: "error body is null")
            val errorCode = errorBody?.getErrorCode()
            errorCode?.let {
                emit(Resource.Error(message = ResponseCode.getMessageByCode(errorCode)))
                return@flow
            }
            emit(Resource.Error(e.message ?: "unexpected http error"))
        } catch (e: IOException) {
            emit(Resource.Error(e.message ?: "unexpected io error"))
        } catch (e: Exception) {
            if (e is CancellationException) {
                throw e
            }
            emit(Resource.Error(e.message ?: "unexpected error"))
        }
    }
}