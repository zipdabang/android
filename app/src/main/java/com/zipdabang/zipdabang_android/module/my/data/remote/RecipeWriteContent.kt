package com.zipdabang.zipdabang_android.module.my.data.remote

data class RecipeWriteContent(
    val categoryId: List<Int>,
    val ingredientCount: Int,
    val ingredients: List<RecipeWriteIngredient>,
    val intro: String,
    val name: String,
    val recipeTip: String,
    val stepCount: Int,
    val steps: List<RecipeWriteStep>,
    val time: String
)