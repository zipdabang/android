package com.zipdabang.zipdabang_android.module.recipes.domain

import com.zipdabang.zipdabang_android.module.recipes.data.preference.PreferenceResultDto

interface PreferenceToggleRepository {
    suspend fun toggleLike(accessToken: String, recipeId: Int): PreferenceResultDto
    suspend fun toggleScrap(accessToken: String, recipeId: Int): PreferenceResultDto
}