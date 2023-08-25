package com.zipdabang.zipdabang_android.module.recipes.use_case

import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.module.recipes.domain.PreferenceToggle
import com.zipdabang.zipdabang_android.module.recipes.domain.RecipeListRepository
import com.zipdabang.zipdabang_android.module.recipes.mapper.toPreferenceToggle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class ToggleScrapListUseCase @Inject constructor(
    private val recipeListRepository: RecipeListRepository
) {
    operator fun invoke(
        accessToken: String, recipeId: Int
    ): Flow<Resource<PreferenceToggle>> = flow {
        try {
            emit(Resource.Loading())
            val remoteResult = recipeListRepository
                .toggleScrapRemote(accessToken = accessToken, recipeId = recipeId)
            if (remoteResult.isSuccess && remoteResult.result != null) {
                val localResult = recipeListRepository.toggleScrapLocal(recipeId, remoteResult)
                    .toPreferenceToggle()
                emit(
                    Resource.Success(
                        code = localResult.code,
                        message = localResult.message,
                        data = localResult
                    )
                )
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