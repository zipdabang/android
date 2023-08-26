package com.zipdabang.zipdabang_android.module.recipes.use_case

import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.module.recipes.domain.RecipeListRepository
import com.zipdabang.zipdabang_android.module.recipes.domain.RecipePreview
import com.zipdabang.zipdabang_android.module.recipes.mapper.toRecipePreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetRecipePreviewUseCase @Inject constructor(
    private val repository: RecipeListRepository
) {
    operator fun invoke(
        accessToken: String, ownerType: String
    ): Flow<Resource<RecipePreview>> = flow {
        try {
            emit(Resource.Loading())
            val previewData = repository.getRecipePreviewList(
                accessToken = accessToken,
                ownerType = ownerType
            ).toRecipePreview()

            emit(
                Resource.Success(
                    data = previewData,
                    code = previewData.code,
                    message = previewData.message
                )
            )
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = e.localizedMessage ?: "http exception"
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = e.localizedMessage ?: "io exception"
                )
            )
        }
    }
}