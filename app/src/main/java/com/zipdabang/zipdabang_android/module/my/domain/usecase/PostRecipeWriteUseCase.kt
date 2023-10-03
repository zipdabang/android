package com.zipdabang.zipdabang_android.module.my.domain.usecase

import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.module.my.data.remote.RecipeWriteRequest
import com.zipdabang.zipdabang_android.module.my.data.remote.RecipeWriteResponse
import com.zipdabang.zipdabang_android.module.my.data.remote.RecipeWriteResult
import com.zipdabang.zipdabang_android.module.my.domain.repository.MyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PostRecipeWriteUseCase @Inject constructor(
    private val repository: MyRepository
) {
    operator fun invoke(accessToken : String, recipeWriteForm : RecipeWriteRequest) : Flow<Resource<RecipeWriteResult>> = flow{
        try{
            emit(Resource.Loading())
            val recipeWriteResponse = repository.postRecipe(accessToken = accessToken, recipeWriteForm = recipeWriteForm)
            emit(Resource.Success(
                data = recipeWriteResponse.result,
                code = recipeWriteResponse.code,
                message = recipeWriteResponse.message
            ))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}