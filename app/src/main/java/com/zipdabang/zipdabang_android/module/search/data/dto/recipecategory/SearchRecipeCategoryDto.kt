package com.zipdabang.zipdabang_android.module.search.data.dto.recipecategory

data class SearchRecipeCategoryDto(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: SearchCategoryResult
)