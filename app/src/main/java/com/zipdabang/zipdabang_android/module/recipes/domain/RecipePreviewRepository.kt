package com.zipdabang.zipdabang_android.module.recipes.domain

import com.zipdabang.zipdabang_android.module.recipes.data.preference.PreferenceResultDto
import com.zipdabang.zipdabang_android.module.recipes.data.preview.RecipePreviewItemsDto

interface RecipePreviewRepository {
    suspend fun getRecipePreviewList(accessToken: String, ownerType: String): RecipePreviewItemsDto
}