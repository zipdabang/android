package com.zipdabang.zipdabang_android.module.detail.recipe.use_case

import android.util.Log
import androidx.datastore.core.DataStore
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.common.ResponseCode
import com.zipdabang.zipdabang_android.common.getErrorCode
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.comment.use_case.BlockUserUseCase
import com.zipdabang.zipdabang_android.module.detail.recipe.domain.RecipeDetailRepository
import com.zipdabang.zipdabang_android.module.detail.recipe.domain.RecipeReportResult
import com.zipdabang.zipdabang_android.module.detail.recipe.util.toRecipeReportResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.CancellationException
import javax.inject.Inject

class ReportRecipeUseCase @Inject constructor(
    private val tokens: DataStore<Token>,
    private val repository: RecipeDetailRepository
) {
    companion object {
        const val TAG = "ReportRecipeUseCase"
    }

    operator fun invoke(
        recipeId: Int,
        reportId: Int
    ): Flow<Resource<RecipeReportResult>> = flow {
        try {
            emit(Resource.Loading())

            val accessToken = "Bearer ${tokens.data.first().accessToken}"

            val result = repository.reportRecipe(
                accessToken = accessToken,
                recipeId = recipeId,
                reportId = reportId
            ).toRecipeReportResult()


            when (result.code) {
                ResponseCode.RESPONSE_DEFAULT.code -> {
                    emit(Resource.Success(
                        data = result,
                        code = result.code,
                        message = result.message
                    ))
                    Log.i(TAG, "report successful, $result")
                }
                else -> {
                    emit(Resource.Error(message = result.message))
                    Log.e(TAG, "report failure, $result")
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
            emit(Resource.Error(message =  e.message ?: "unexpected http exception"))
            Log.e(TAG, "report failure, ${e.message}")
        } catch (e: IOException) {
            emit(Resource.Error(message =  e.message ?: "unexpected io exception"))
            Log.e(TAG, "report failure, ${e.message}")
        } catch (e: Exception) {
            if (e is CancellationException) {
                throw e
            }
            emit(Resource.Error(message =  e.message ?: "unexpected exception"))
            Log.e(TAG, "report failure, ${e.message}")
        }
    }
}