package com.zipdabang.zipdabang_android.module.my.data.remote.myrecipes.complete

data class CompleteRecipesResult(
    val currentPageElements: Int,
    val isFirst: Boolean,
    val isLast: Boolean,
    val recipeList: List<CompleteRecipe>,
    val totalElements: Int,
    val totalPage: Int
)