package com.zipdabang.zipdabang_android.module.my.domain.repository

import com.zipdabang.zipdabang_android.module.my.data.remote.RecipeWriteRequest
import com.zipdabang.zipdabang_android.module.my.data.remote.RecipeWriteResponse
import com.zipdabang.zipdabang_android.module.my.data.remote.SignOutResponseDto
import com.zipdabang.zipdabang_android.module.my.data.remote.SignOutTokens

interface MyRepository {
    suspend fun signOut(accessToken: String): SignOutResponseDto
    suspend fun postRecipe(accessToken: String, recipeWriteForm : RecipeWriteRequest) : RecipeWriteResponse
}