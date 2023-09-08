package com.zipdabang.zipdabang_android.module.search.data.dto.searchpreview

import com.zipdabang.zipdabang_android.module.search.data.dto.common.SearchRecipe

data class SearchCategoryList(
    val categoryId: Int,
    val elements: Int,
    val recipeList: List<SearchRecipe>
)