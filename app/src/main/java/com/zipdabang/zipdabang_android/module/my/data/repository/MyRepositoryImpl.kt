package com.zipdabang.zipdabang_android.module.my.data.repository

import com.zipdabang.zipdabang_android.module.my.data.remote.MyApi
import com.zipdabang.zipdabang_android.module.my.data.remote.RecipeWriteRequest
import com.zipdabang.zipdabang_android.module.my.data.remote.RecipeWriteResponse
import com.zipdabang.zipdabang_android.module.my.data.remote.SignOutResponseDto
import com.zipdabang.zipdabang_android.module.my.data.remote.SignOutTokens
import com.zipdabang.zipdabang_android.module.my.domain.repository.MyRepository
import javax.inject.Inject

class MyRepositoryImpl @Inject constructor(
    private val api : MyApi
) : MyRepository{
    override suspend fun signOut(accessToken: String, ): SignOutResponseDto {
        return api.signOut(accessToken)
    }

    override suspend fun postRecipe(
        accessToken: String,
        recipeWriteForm: RecipeWriteRequest
    ): RecipeWriteResponse {
        return api.postRecipe(accessToken, recipeWriteForm)
    }
}