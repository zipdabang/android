package com.zipdabang.zipdabang_android.module.recipes.domain

import com.zipdabang.zipdabang_android.module.recipes.data.category.RecipeCategoryDto

interface RecipeCategoryRepository {
    suspend fun getCategoryItems(accessToken: String): RecipeCategoryDto
}