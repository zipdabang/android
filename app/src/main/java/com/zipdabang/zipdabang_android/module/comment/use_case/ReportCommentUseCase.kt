package com.zipdabang.zipdabang_android.module.comment.use_case

import android.util.Log
import androidx.datastore.core.DataStore
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.common.ResponseCode
import com.zipdabang.zipdabang_android.common.getErrorCode
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.comment.domain.RecipeCommentRepository
import com.zipdabang.zipdabang_android.module.comment.domain.ReportResult
import com.zipdabang.zipdabang_android.module.comment.util.toReportResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.CancellationException
import javax.inject.Inject

class ReportCommentUseCase @Inject constructor(
    private val repository: RecipeCommentRepository,
    private val tokens: DataStore<Token>
) {
    companion object {
        const val TAG = "ReportCommentUseCase"
    }
    operator fun invoke(
        recipeId: Int,
        commentId: Int,
        reportId: Int
    ): Flow<Resource<ReportResult>> = flow {
        try {
            emit(Resource.Loading())
            val result = tokens.data.first().accessToken?.let {
                val accessToken = "Bearer $it"
                repository.reportComment(
                    accessToken = accessToken,
                    recipeId = recipeId,
                    commentId = commentId,
                    reportId = reportId
                ).toReportResult()
            }

            result?.let {
                when (it.code) {
                    ResponseCode.RESPONSE_DEFAULT.code -> {
                        emit(Resource.Success(
                            data = it,
                            code = it.code,
                            message = it.message
                        ))
                    }
                    else -> {
                        emit(Resource.Error(message = it.message))
                    }
                }
            }
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()
            Log.e(EditCommentUseCase.TAG, errorBody?.string() ?: "error body is null")
            val errorCode = errorBody?.getErrorCode()
            errorCode?.let {
                emit(Resource.Error(message = ResponseCode.getMessageByCode(errorCode)))
                return@flow
            }
            emit(Resource.Error(message = e.message ?: "unexpected http exception"))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.message ?: "unexpected io exception"))
        } catch (e: Exception) {
            if (e is CancellationException) {
                throw e
            }
            emit(Resource.Error(message = e.message ?: "unexpected exception"))
        }
    }
}