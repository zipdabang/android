package com.zipdabang.zipdabang_android.module.recipes.use_case

import android.util.Log
import androidx.datastore.core.DataStore
import com.zipdabang.zipdabang_android.common.Constants.TOKEN_NULL
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.common.ResponseCode
import com.zipdabang.zipdabang_android.common.getErrorCode
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.login.use_case.GetTempLoginResultUseCase
import com.zipdabang.zipdabang_android.module.recipes.domain.PreferenceToggleRepository
import com.zipdabang.zipdabang_android.module.recipes.domain.PreferenceToggle
import com.zipdabang.zipdabang_android.module.recipes.mapper.toPreferenceToggle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import okhttp3.internal.notify
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ToggleLikeUseCase @Inject constructor(
    private val tokenDataStore: DataStore<Token>,
    private val repository: PreferenceToggleRepository
) {

    companion object {
        const val TAG = "ToggleLikeUseCase"
    }
    operator fun invoke(
        recipeId: Int
    ): Flow<Resource<PreferenceToggle>> = flow {
        try {
            val accessToken = ("Bearer " + tokenDataStore.data.first().accessToken)

            emit(Resource.Loading())

            val likeToggleResult = repository.toggleLike(
                accessToken = accessToken,
                recipeId = recipeId
            ).toPreferenceToggle()

            emit(
                Resource.Success(
                    data = likeToggleResult,
                    code = likeToggleResult.code,
                    message = likeToggleResult.message
                )
            )

            Log.d(TAG, "like result : $likeToggleResult")
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()
            val errorCode = errorBody?.getErrorCode()
            errorCode?.let {
                emit(Resource.Error(message = ResponseCode.getMessageByCode(errorCode)))
                return@flow
            }
            emit(Resource.Error(e.localizedMessage ?: "unexpected error"))
            Log.d(TAG, "like result : ${e.localizedMessage}")
        } catch (e: IOException) {
            emit(Resource.Error("couldn't reach server, check internet connection"))
        }
    }
}