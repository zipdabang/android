package com.zipdabang.zipdabang_android.module.search.data.dto

data class SearchCategoryList(
    val categoryId: Int,
    val elements: Int,
    val recipeList: List<SearchRecipe>
)