package com.zipdabang.zipdabang_android.module.my.data.remote.recipeedit.temp

data class GetTempRecipeResult(
    val ingredients: List<GetTempRecipeIngredient>,
    val recipeInfo: GetTempRecipeInfo,
    val steps: List<GetTempRecipeStep>
)