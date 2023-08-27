package com.zipdabang.zipdabang_android.module.recipes.use_case

import androidx.datastore.core.DataStore
import com.zipdabang.zipdabang_android.common.Constants.TOKEN_NULL
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.recipes.common.OwnerType
import com.zipdabang.zipdabang_android.module.recipes.domain.RecipeListRepository
import com.zipdabang.zipdabang_android.module.recipes.domain.RecipePreview
import com.zipdabang.zipdabang_android.module.recipes.mapper.toRecipePreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetRecipePreviewUseCase @Inject constructor(
    private val tokenDataStore: DataStore<Token>,
    private val repository: RecipeListRepository
) {
    operator fun invoke(
        ownerType: OwnerType
    ): Flow<Resource<RecipePreview>> = flow {
        try {
            val accessToken = tokenDataStore.data.first().accessToken ?: TOKEN_NULL

            emit(Resource.Loading())
            val previewData = repository.getRecipePreviewList(
                accessToken = accessToken,
                ownerType = ownerType.type
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