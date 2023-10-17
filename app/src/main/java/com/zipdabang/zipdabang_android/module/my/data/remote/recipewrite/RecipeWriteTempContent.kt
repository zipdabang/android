package com.zipdabang.zipdabang_android.module.my.data.remote.recipewrite

data class RecipeWriteTempContent(
    val ingredientCount: Int?,
    val ingredients: List<RecipeWriteTempIngredient>?,
    val intro: String?,
    val name: String?,
    val recipeTip: String?,
    val stepCount: Int?,
    val steps: List<RecipeWriteTempStep>?,
    val thumbnailUrl: String?,
    val time: String?
)