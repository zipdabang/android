package com.zipdabang.zipdabang_android.module.my.data.remote.otherinfo

data class OtherRecipeListResult(
    val currentPageElements: Int,
    val isFirst: Boolean,
    val isLast: Boolean,
    val recipeList: List<OtherRecipe>,
    val totalElements: Int,
    val totalPage: Int
)