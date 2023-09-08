package com.zipdabang.zipdabang_android.module.search.ui

import com.zipdabang.zipdabang_android.module.search.data.dto.searchpreview.SearchCategoryList

data class SearchState(
    val isLoading: Boolean = false,
    val searchList: List<SearchCategoryList> = emptyList(),
    val isError : Boolean = false,
    val error: String = ""
)
