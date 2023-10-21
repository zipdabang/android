package com.zipdabang.zipdabang_android.module.detail.recipe.use_case

import android.util.Log
import androidx.datastore.core.DataStore
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.common.ResponseCode
import com.zipdabang.zipdabang_android.common.getErrorCode
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.detail.recipe.domain.RecipeDeleteResult
import com.zipdabang.zipdabang_android.module.detail.recipe.domain.RecipeDetailDomain
import com.zipdabang.zipdabang_android.module.detail.recipe.domain.RecipeDetailRepository
import com.zipdabang.zipdabang_android.module.detail.recipe.util.toRecipeDeleteResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.CancellationException
import javax.inject.Inject

class DeleteRecipeUseCase @Inject constructor(
    private val tokenDataStore: DataStore<Token>,
    private val repository: RecipeDetailRepository
) {
    operator fun invoke(
        recipeId: Int
    ): Flow<Resource<RecipeDeleteResult>> = flow {
        try {
            val accessToken = "Bearer ${tokenDataStore.data.first().accessToken}"

            val deleteResult = repository.deleteRecipe(
                accessToken = accessToken,
                recipeId = recipeId
            ).toRecipeDeleteResult()

            emitDataByResponseCode(deleteResult)

        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()
            val errorCode = errorBody?.getErrorCode()
            errorCode?.let {
                emit(Resource.Error(message = ResponseCode.getMessageByCode(errorCode)))
                return@flow
            }
            emit(Resource.Error(
                message = e.message ?: "unexpected http exception"
            ))
        } catch (e: IOException) {
            emit(Resource.Error(
                message = e.message ?: "unexpected io error"
            ))
        } catch (e: Exception) {
            if (e is CancellationException) {
                throw e
            }
            emit(Resource.Error(
                message = e.message ?: "unexpected error"
            ))
        }
    }

    private suspend fun FlowCollector<Resource<RecipeDeleteResult>>.emitDataByResponseCode(
        deleteResult: RecipeDeleteResult
    ) {
        when (deleteResult.code) {
            ResponseCode.RESPONSE_DEFAULT.code -> {
                emit(
                    Resource.Success(
                        data = deleteResult,
                        code = ResponseCode.RESPONSE_DEFAULT.code,
                        message = ResponseCode.RESPONSE_DEFAULT.message
                    )
                )
            }

            ResponseCode.UNAUTHORIZED_TOKEN_UNUSUAL.code -> {
                emit(
                    Resource.Error(
                        message = ResponseCode.UNAUTHORIZED_TOKEN_UNUSUAL.message
                    )
                )
            }

            ResponseCode.UNAUTHORIZED_ACCESS_EXPIRED.code -> {
                emit(
                    Resource.Error(
                        message = ResponseCode.UNAUTHORIZED_ACCESS_EXPIRED.message
                    )
                )
            }

            ResponseCode.UNAUTHORIZED_TOKEN_NOT_EXISTS.code -> {
                emit(
                    Resource.Error(
                        message = ResponseCode.UNAUTHORIZED_TOKEN_NOT_EXISTS.message
                    )
                )
            }

            ResponseCode.BAD_REQUEST_USER_NOT_EXISTS.code -> {
                emit(
                    Resource.Error(
                        message = ResponseCode.BAD_REQUEST_USER_NOT_EXISTS.message
                    )
                )
            }

            ResponseCode.BAD_REQUEST_RECIPE_NOT_EXISTS.code -> {
                emit(
                    Resource.Error(
                        message = ResponseCode.BAD_REQUEST_RECIPE_NOT_EXISTS.message
                    )
                )
            }

            ResponseCode.BAD_REQUEST_RECIPE_BANNED.code -> {
                emit(
                    Resource.Error(
                        message = ResponseCode.BAD_REQUEST_RECIPE_BANNED.message
                    )
                )
            }

            ResponseCode.SERVER_ERROR.code -> {
                emit(
                    Resource.Error(
                        message = ResponseCode.SERVER_ERROR.message
                    )
                )
            }

            else -> {
                emit(
                    Resource.Error(
                        message = "unexpected error"
                    )
                )
            }
        }
    }
}