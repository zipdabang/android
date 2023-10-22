package com.zipdabang.zipdabang_android.module.detail.recipe.use_case

import android.util.Log
import androidx.datastore.core.DataStore
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.common.ResponseCode
import com.zipdabang.zipdabang_android.common.getErrorCode
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.comment.domain.UserBlockResult
import com.zipdabang.zipdabang_android.module.comment.use_case.BlockUserUseCase
import com.zipdabang.zipdabang_android.module.comment.util.toUserBlockResult
import com.zipdabang.zipdabang_android.module.detail.recipe.domain.RecipeDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class CancelBlockUseCase @Inject constructor(
    private val tokenDataStore: DataStore<Token>,
    private val repository: RecipeDetailRepository
) {
    operator fun invoke(userId: Int): Flow<Resource<UserBlockResult>> = flow {
        try {
            val accessToken = "Bearer ${tokenDataStore.data.first().accessToken}"
            val result = repository.cancelUserBlock(accessToken, userId).toUserBlockResult()

            when (result.code) {
                ResponseCode.RESPONSE_DEFAULT.code -> {
                    emit(Resource.Success(
                        data = result,
                        code = result.code,
                        message = result.message
                    ))
                }
                else -> {
                    emit(Resource.Error(message = result.message))
                }
            }

        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()
            val errorCode = errorBody?.getErrorCode()
            errorCode?.let {
                emit(Resource.Error(message = ResponseCode.getMessageByCode(errorCode)))
                return@flow
            }
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