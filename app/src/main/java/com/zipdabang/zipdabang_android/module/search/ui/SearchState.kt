package com.zipdabang.zipdabang_android.module.search.ui

import com.zipdabang.zipdabang_android.module.search.data.dto.searchpreview.recipeList

data class SearchState(
    val isLoading: Boolean = false,
    val searchList: List<recipeList> = emptyList(),
    val count : Int = 0,
    val isError : Boolean = false,
    val error: String = ""
)
