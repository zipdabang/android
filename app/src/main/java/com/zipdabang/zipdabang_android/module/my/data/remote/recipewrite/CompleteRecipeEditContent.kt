package com.zipdabang.zipdabang_android.module.my.data.remote.recipewrite

data class CompleteRecipeEditContent(
    val categoryId: List<Int>,
    val ingredientCount: Int,
    val ingredients: List<CompleteRecipeEditIngredient>,
    val intro: String,
    val name: String,
    val recipeTip: String,
    val stepCount: Int,
    val steps: List<CompleteRecipeEditStep>,
    val time: String
)