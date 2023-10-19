package com.zipdabang.zipdabang_android.module.my.data.remote.recipeedit.complete

data class Content(
    val categoryId: List<Int>,
    val ingredientCount: Int,
    val ingredients: List<Ingredient>,
    val intro: String,
    val name: String,
    val recipeTip: String,
    val stepCount: Int,
    val steps: List<Step>,
    val time: String
)