package com.zipdabang.zipdabang_android.module.recipes.mapper

import com.zipdabang.zipdabang_android.module.recipes.data.banner.RecipeBannerDto
import com.zipdabang.zipdabang_android.module.recipes.data.category.RecipeCategoryDto
import com.zipdabang.zipdabang_android.module.recipes.data.preference.PreferenceResultDto
import com.zipdabang.zipdabang_android.module.recipes.data.preview.RecipePreviewItemsDto
import com.zipdabang.zipdabang_android.module.recipes.data.recipe_list.RecipeListDto
import com.zipdabang.zipdabang_android.module.recipes.domain.PreferenceToggle
import com.zipdabang.zipdabang_android.module.recipes.domain.RecipeBanner
import com.zipdabang.zipdabang_android.module.recipes.domain.RecipeCategoryItems
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

fun RecipeCategoryDto.toRecipeCategoryItems(): RecipeCategoryItems {
    return RecipeCategoryItems(
        code = code,
        isSuccessful = isSuccess,
        message = message,
        categoryList = recipeCategoryResult?.beverageCategoryList
    )
}

fun RecipeListDto.toRecipePreview(): RecipePreview {
    return RecipePreview(
        code = code,
        isSuccessful = isSuccess,
        message = message,
        recipeList = result.recipeList
    )
}

fun RecipeBannerDto.toRecipeBanner(): RecipeBanner {
    return RecipeBanner(
        code = code,
        isSuccessful = isSuccess,
        message = message,
        recipeBanners = result.bannerList
    )
}