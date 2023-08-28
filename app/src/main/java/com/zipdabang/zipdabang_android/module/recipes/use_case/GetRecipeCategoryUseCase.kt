package com.zipdabang.zipdabang_android.module.recipes.use_case

import androidx.datastore.core.DataStore
import com.zipdabang.zipdabang_android.common.Constants.TOKEN_NULL
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
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
    operator fun invoke(): Flow<Resource<RecipeCategoryItems>> = flow {
        val accessToken = tokenDataStore.data.first().accessToken ?: TOKEN_NULL

        try {
            emit(Resource.Loading())
            val result =
                recipeCategoryRepository.getCategoryItems(accessToken).toRecipeCategoryItems()
            emit(Resource.Success(data = result, code = result.code, message = result.message))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.message ?: "unexpected http exception"))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.message ?: "unexpected io exception"))
        }
    }
}