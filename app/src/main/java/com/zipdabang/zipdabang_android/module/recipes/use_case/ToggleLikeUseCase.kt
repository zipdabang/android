package com.zipdabang.zipdabang_android.module.recipes.use_case

import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.module.recipes.domain.PreferenceToggleRepository
import com.zipdabang.zipdabang_android.module.recipes.domain.PreferenceToggle
import com.zipdabang.zipdabang_android.module.recipes.mapper.toPreferenceToggle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ToggleLikeUseCase @Inject constructor(
    private val repository: PreferenceToggleRepository
) {
    operator fun invoke(
        accessToken: String,
        recipeId: Int
    ): Flow<Resource<PreferenceToggle>> = flow {
        try {
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
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "unexpected error"))
        } catch (e: IOException) {
            emit(Resource.Error("couldn't reach server, check internet connection"))
        }
    }
}