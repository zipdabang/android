package com.zipdabang.zipdabang_android.module.my.data.remote.myrecipes.scraplike

data class GetScrapRecipesResult(
    val currentPageElements: Int,
    val isFirst: Boolean,
    val isLast: Boolean,
    val recipeList: List<GetScrapRecipe>,
    val totalElements: Int,
    val totalPage: Int
)