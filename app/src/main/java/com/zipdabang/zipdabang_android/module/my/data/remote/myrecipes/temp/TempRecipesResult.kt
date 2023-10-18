package com.zipdabang.zipdabang_android.module.my.data.remote.myrecipes.temp

data class TempRecipesResult(
    val currentPageElements: Int,
    val isFirst: Boolean,
    val isLast: Boolean,
    val tempRecipeList: List<TempRecipe>,
    val totalElements: Int,
    val totalPage: Int
)