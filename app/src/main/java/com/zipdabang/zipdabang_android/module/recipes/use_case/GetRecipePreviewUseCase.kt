package com.zipdabang.zipdabang_android.module.recipes.use_case

import android.util.Log
import androidx.datastore.core.DataStore
import com.google.gson.JsonParseException
import com.zipdabang.zipdabang_android.common.Constants.TOKEN_NULL
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.common.ResponseCode
import com.zipdabang.zipdabang_android.common.getErrorCode
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.login.use_case.GetTempLoginResultUseCase
import com.zipdabang.zipdabang_android.module.recipes.common.OwnerType
import com.zipdabang.zipdabang_android.module.recipes.domain.RecipeListRepository
import com.zipdabang.zipdabang_android.module.recipes.domain.RecipePreview
import com.zipdabang.zipdabang_android.module.recipes.mapper.toRecipePreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import org.json.JSONException
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetRecipePreviewUseCase @Inject constructor(
    private val tokenDataStore: DataStore<Token>,
    private val repository: RecipeListRepository
) {
    companion object {
        const val TAG = "GetRecipePreviewUseCase"
    }

    operator fun invoke(
        ownerType: OwnerType
    ): Flow<Resource<RecipePreview>> = flow {
        try {
            val accessToken = ("Bearer " + tokenDataStore.data.first().accessToken)

            emit(Resource.Loading())
/*            val previewData = repository.getRecipePreviewList(
                accessToken = accessToken,
                ownerType = ownerType.type
            ).toRecipePreview()*/
/*
            Log.d(TAG, "data : $previewData")

            emit(
                Resource.Success(
                    data = previewData,
                    code = previewData.code,
                    message = previewData.message
                )
            )*/
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()
            Log.e(TAG, errorBody?.string() ?: "error body is null")
            val errorCode = errorBody?.getErrorCode()
            errorCode?.let {
                emit(Resource.Error(message = ResponseCode.getMessageByCode(errorCode)))
                return@flow
            }

            emit(
                Resource.Error(
                    message = e.localizedMessage ?: "http exception"
                )
            )
            Log.d(TAG, "error : ${e.message} ${e.response()}")
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = e.localizedMessage ?: "io exception"
                )
            )
            Log.d(TAG, "error : ${e.message}")
        } catch (_: JsonParseException) {

        }
    }
}