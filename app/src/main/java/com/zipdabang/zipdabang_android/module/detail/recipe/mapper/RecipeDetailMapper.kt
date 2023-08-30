package com.zipdabang.zipdabang_android.module.detail.recipe.mapper

import com.zipdabang.zipdabang_android.module.detail.recipe.data.RecipeDetailDto
import com.zipdabang.zipdabang_android.module.detail.recipe.domain.RecipeDetailDomain

fun RecipeDetailDto.toRecipeDetailDomain(): RecipeDetailDomain {
    return RecipeDetailDomain(
        code = code,
        isSuccessful = isSuccess,
        message = message,
        isOwner = result?.owner,
        profileUrl = result?.recipeInfo?.ownerImage,
        recipeInfo = result?.recipeInfo,
        recipeSteps = result?.steps,
        recipeIngredients = result?.ingredients
    )
}