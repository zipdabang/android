package com.zipdabang.zipdabang_android.module.my.data.remote.otherinfo.recipe

import com.zipdabang.zipdabang_android.module.my.data.remote.otherinfo.OtherRecipe

data class OtherRecipeListResult(
    val currentPageElements: Int,
    val isFirst: Boolean,
    val isLast: Boolean,
    val recipeList: List<OtherListRecipe>,
    val totalElements: Int,
    val totalPage: Int
)