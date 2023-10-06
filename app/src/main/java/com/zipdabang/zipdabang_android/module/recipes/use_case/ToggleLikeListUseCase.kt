package com.zipdabang.zipdabang_android.module.recipes.use_case

import androidx.datastore.core.DataStore
import com.zipdabang.zipdabang_android.common.Constants.TOKEN_NULL
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.recipes.domain.PreferenceToggle
import com.zipdabang.zipdabang_android.module.recipes.domain.RecipeListRepository
import com.zipdabang.zipdabang_android.module.recipes.mapper.toPreferenceToggle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class ToggleLikeListUseCase @Inject constructor(
    private val recipeListRepository: RecipeListRepository,
    private val tokenDataStore: DataStore<Token>
) {
    operator fun invoke(
        recipeId: Int,
        categoryId: Int?,
        ownerType: String?
    ): Flow<Resource<PreferenceToggle>> = flow {
        try {
            val accessToken = ("Bearer " + tokenDataStore.data.first().accessToken)

            emit(Resource.Loading())
            val remoteResult = recipeListRepository
                .toggleLikeRemote(accessToken = accessToken, recipeId = recipeId)
            if (remoteResult.isSuccess && remoteResult.result != null) {
                if (categoryId != null && ownerType == null) {
                    val localResult = recipeListRepository
                        .toggleLikeLocalByCategory(recipeId, remoteResult, categoryId)
                        .toPreferenceToggle()
                    emit(Resource.Success(
                        code = localResult.code,
                        message = localResult.message,
                        data = localResult
                    ))
                } else if (categoryId == null && ownerType != null) {
                    val localResult = recipeListRepository
                        .toggleLikeLocalByUser(recipeId, remoteResult, ownerType)
                        .toPreferenceToggle()
                    emit(Resource.Success(
                        code = localResult.code,
                        message = localResult.message,
                        data = localResult
                    ))
                }
            } else {
                emit(Resource.Error(message = "network failure"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.message ?: "unknown http exception"))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.message ?: "unknown io exception"))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: "unknown io exception"))
        }

    }
}