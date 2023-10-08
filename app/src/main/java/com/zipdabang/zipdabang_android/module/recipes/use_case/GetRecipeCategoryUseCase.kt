package com.zipdabang.zipdabang_android.module.recipes.use_case

import android.util.Log
import androidx.datastore.core.DataStore
import com.zipdabang.zipdabang_android.common.Constants.TOKEN_NULL
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.common.ResponseCode
import com.zipdabang.zipdabang_android.common.getErrorCode
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.login.use_case.GetTempLoginResultUseCase
import com.zipdabang.zipdabang_android.module.recipes.domain.RecipeCategoryItems
import com.zipdabang.zipdabang_android.module.recipes.domain.RecipeCategoryRepository
import com.zipdabang.zipdabang_android.module.recipes.mapper.toRecipeCategoryItems
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetRecipeCategoryUseCase @Inject constructor(
    private val tokenDataStore: DataStore<Token>,
    private val recipeCategoryRepository: RecipeCategoryRepository
) {
    companion object {
        const val TAG = "GetRecipeCategoryUseCase"
    }

    operator fun invoke(): Flow<Resource<RecipeCategoryItems>> = flow {
        val accessToken = ("Bearer " + tokenDataStore.data.first().accessToken)
        Log.d(TAG, "access token : $accessToken")

        try {
            emit(Resource.Loading())
            val result =
                recipeCategoryRepository.getCategoryItems(accessToken).toRecipeCategoryItems()
            Log.d(TAG, "result : $result")
            emit(Resource.Success(data = result, code = result.code, message = result.message))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()
            Log.e(TAG, errorBody?.string() ?: "error body is null")
            val errorCode = errorBody?.getErrorCode()
            errorCode?.let {
                emit(Resource.Error(message = ResponseCode.getMessageByCode(errorCode)))
                return@flow
            }
            Log.e(TAG, "exception occured, ${e.message}, ${e.response()}")
            emit(Resource.Error(message = e.message ?: "unexpected http exception"))
        } catch (e: IOException) {
            Log.e(TAG, "exception occured, ${e.message}")
            emit(Resource.Error(message = e.message ?: "unexpected io exception"))
        }
    }
}