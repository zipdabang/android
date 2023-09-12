package com.zipdabang.zipdabang_android.module.comment.use_case

import androidx.datastore.core.DataStore
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.common.ResponseCode
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.comment.domain.EditResult
import com.zipdabang.zipdabang_android.module.comment.domain.RecipeCommentRepository
import com.zipdabang.zipdabang_android.module.comment.util.toEditResult
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.CancellationException
import javax.inject.Inject

class EditCommentUseCase @Inject constructor(
    private val repository: RecipeCommentRepository,
    private val tokenDataStore: DataStore<Token>
) {
    operator fun invoke(
        recipeId: Int,
        commentId: Int,
        newContent: String
    ) = flow<Resource<EditResult>> {
        try {
            emit(Resource.Loading())

            val accessToken = "Bearer ${tokenDataStore.data.first().accessToken}"

            val result = repository.editRecipeComment(
                accessToken = accessToken,
                recipeId = recipeId,
                commentId = commentId,
                newContent = newContent
            ).toEditResult()

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