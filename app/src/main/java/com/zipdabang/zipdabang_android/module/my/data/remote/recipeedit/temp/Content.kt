package com.zipdabang.zipdabang_android.module.my.data.remote.recipeedit.temp

data class Content(
    val ingredientCount: Int,
    val ingredients: List<Ingredient>,
    val intro: String,
    val name: String,
    val recipeTip: String,
    val stepCount: Int,
    val steps: List<Step>,
    val thumbnailUrl: String,
    val time: String
)