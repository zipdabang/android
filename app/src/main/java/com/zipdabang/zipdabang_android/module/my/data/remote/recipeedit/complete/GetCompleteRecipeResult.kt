package com.zipdabang.zipdabang_android.module.my.data.remote.recipeedit.complete

data class GetCompleteRecipeResult(
    val ingredients: List<GetCompleteRecipeIngredient>,
    val owner: Boolean,
    val ownerId: Int,
    val recipeInfo: GetCompleteRecipeInfo,
    val steps: List<GetCompleteRecipeStep>
)