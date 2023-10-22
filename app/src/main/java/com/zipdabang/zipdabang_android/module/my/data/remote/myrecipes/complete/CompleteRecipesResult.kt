package com.zipdabang.zipdabang_android.module.my.data.remote.myrecipes.complete

data class CompleteRecipesResult(
    val recipeList: List<CompleteRecipe>,
    val currentPageElements: Int,
    val isFirst: Boolean,
    val isLast: Boolean,
    val totalElements: Int,
    val totalPage: Int
)