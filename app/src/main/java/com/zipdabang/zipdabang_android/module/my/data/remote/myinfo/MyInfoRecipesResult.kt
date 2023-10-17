package com.zipdabang.zipdabang_android.module.my.data.remote.myinfo

data class MyInfoRecipesResult(
    val currentPageElements: Int,
    val isFirst: Boolean,
    val isLast: Boolean,
    val recipeList: List<Recipe>,
    val totalElements: Int,
    val totalPage: Int
)