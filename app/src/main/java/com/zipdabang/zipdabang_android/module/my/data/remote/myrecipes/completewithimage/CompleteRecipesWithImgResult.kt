package com.zipdabang.zipdabang_android.module.my.data.remote.myrecipes.completewithimage

data class CompleteRecipesWithImgResult(
    val currentPageElements: Int,
    val isFirst: Boolean,
    val isLast: Boolean,
    val recipeList: List<CompleteRecipeWithImg>,
    val totalElements: Int,
    val totalPage: Int
)