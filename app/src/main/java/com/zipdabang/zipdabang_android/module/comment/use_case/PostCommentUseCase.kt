package com.zipdabang.zipdabang_android.module.comment.use_case

import android.util.Log
import androidx.datastore.core.DataStore
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.common.ResponseCode
import com.zipdabang.zipdabang_android.common.getErrorCode
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.comment.data.remote.PostCommentContent
import com.zipdabang.zipdabang_android.module.comment.domain.PostResult
import com.zipdabang.zipdabang_android.module.comment.domain.RecipeCommentRepository
import com.zipdabang.zipdabang_android.module.comment.util.toPostResult
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.CancellationException
import javax.inject.Inject

class PostCommentUseCase @Inject constructor(
    private val recipeCommentRepository: RecipeCommentRepository,
    private val tokenDataStore: DataStore<Token>
) {
    companion object {
        const val TAG = "PostCommentUseCase"
    }

    operator fun invoke(
        recipeId: Int, commentBody: String
    ) = flow {
        try {
            val accessToken = ("Bearer " + tokenDataStore.data.first().accessToken)

            emit(Resource.Loading())

            val response = recipeCommentRepository
                .postRecipeComment(accessToken, recipeId, commentBody)
                .toPostResult()


            val emitData = emitSubmitResult(response)

            emit(emitData)

        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()
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

    private fun emitSubmitResult(
        response: PostResult
    ): Resource<PostResult> {
        return when (response.code) {
            ResponseCode.RESPONSE_DEFAULT.code -> {
                Resource.Success(
                    data = response,
                    code = response.code,
                    message = response.message
                )
            }
            else -> {
                Resource.Error(
                    message = response.message
                )
            }
        }
    }
}