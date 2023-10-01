package com.zipdabang.zipdabang_android.module.detail.recipe.use_case

import androidx.datastore.core.DataStore
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.common.ResponseCode
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
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
    operator fun invoke(
        recipeId: Int,
        reportId: Int
    ): Flow<Resource<RecipeReportResult>> = flow {
        try {
            emit(Resource.Loading())

            val result = tokens.data.first().accessToken?.let {
                val accessToken = "Bearer $it"
                repository.reportRecipe(
                    accessToken = accessToken,
                    recipeId = recipeId,
                    reportId = reportId
                ).toRecipeReportResult()
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
            emit(Resource.Error(message =  e.message ?: "unexpected http exception"))
        } catch (e: IOException) {
            emit(Resource.Error(message =  e.message ?: "unexpected io exception"))
        } catch (e: Exception) {
            if (e is CancellationException) {
                throw e
            }
            emit(Resource.Error(message =  e.message ?: "unexpected exception"))
        }
    }
}