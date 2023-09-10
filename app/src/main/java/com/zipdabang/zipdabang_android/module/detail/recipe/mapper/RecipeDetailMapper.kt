package com.zipdabang.zipdabang_android.module.detail.recipe.mapper

import com.zipdabang.zipdabang_android.module.detail.recipe.data.RecipeDetailDto
import com.zipdabang.zipdabang_android.module.detail.recipe.domain.RecipeDetailDomain

fun RecipeDetailDto.toRecipeDetailDomain(): RecipeDetailDomain {
    return RecipeDetailDomain(
        code = code,
        isSuccessful = isSuccess,
        message = message,
        isOwner = result?.owner,
        // null일 때 기본이미지
        profileUrl = result?.recipeInfo?.ownerImage ?: "https://github.com/kmkim2689/flow-practice/assets/101035437/56eeb15a-f5e3-4b8e-8b5d-993d82d54c5a",
        recipeInfo = result?.recipeInfo,
        recipeSteps = result?.steps,
        recipeIngredients = result?.ingredients
    )
}