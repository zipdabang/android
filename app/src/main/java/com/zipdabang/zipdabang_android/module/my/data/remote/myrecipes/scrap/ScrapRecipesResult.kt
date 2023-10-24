package com.zipdabang.zipdabang_android.module.my.data.remote.myrecipes.scrap

data class ScrapRecipesResult(
    val currentPageElements: Int,
    val isFirst: Boolean,
    val isLast: Boolean,
    val recipeList: List<ScrapRecipe>,
    val totalElements: Int,
    val totalPage: Int
)