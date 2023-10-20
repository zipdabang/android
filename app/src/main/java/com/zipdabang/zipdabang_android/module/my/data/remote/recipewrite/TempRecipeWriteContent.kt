package com.zipdabang.zipdabang_android.module.my.data.remote.recipewrite

import okhttp3.MultipartBody

data class TempRecipeWriteContent(
    val ingredientCount: Int,
    val ingredients: List<TempRecipeWriteIngredient>,
    val intro: String?,
    val name: String?,
    val recipeTip: String?,
    val stepCount: Int,
    val steps: List<TempRecipeWriteStep>,
    val thumbnailUrl: String?,
    val time: String?
)