package com.zipdabang.zipdabang_android.module.recipes.mapper

import com.zipdabang.zipdabang_android.module.recipes.data.preference.PreferenceResultDto
import com.zipdabang.zipdabang_android.module.recipes.data.preview.RecipePreviewItemsDto
import com.zipdabang.zipdabang_android.module.recipes.domain.PreferenceToggle
import com.zipdabang.zipdabang_android.module.recipes.domain.RecipePreview

fun RecipePreviewItemsDto.toRecipePreview(): RecipePreview {
    return RecipePreview(
        code = code,
        isSuccessful = isSuccess,
        message = message,
        recipeList = recipeResult?.recipeList
    )
}

fun PreferenceResultDto.toPreferenceToggle(): PreferenceToggle {
    return PreferenceToggle(
        code = code,
        isSuccessful = isSuccess,
        message = message,
        recipeId = result?.recipeId
    )
}