package com.zipdabang.zipdabang_android.module.recipes.use_case

import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.module.recipes.domain.RecipeCategoryItems
import com.zipdabang.zipdabang_android.module.recipes.domain.RecipeCategoryRepository
import com.zipdabang.zipdabang_android.module.recipes.mapper.toRecipeCategoryItems
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetRecipeCategoryUseCase @Inject constructor(
    private val recipeCategoryRepository: RecipeCategoryRepository
) {
    operator fun invoke(accessToken: String): Flow<Resource<RecipeCategoryItems>> = flow {
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