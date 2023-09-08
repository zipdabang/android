package com.zipdabang.zipdabang_android.module.search.data.dto.recipecategory

import com.zipdabang.zipdabang_android.module.search.data.dto.common.SearchRecipe

data class SearchCategoryResult(
    val currentPageElements: Int,
    val isFirst: Boolean,
    val isLast: Boolean,
    val recipeList: List<SearchRecipe>,
    val totalElements: Int,
    val totalPage: Int
)