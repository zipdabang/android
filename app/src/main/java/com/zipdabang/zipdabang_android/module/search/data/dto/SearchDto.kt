package com.zipdabang.zipdabang_android.module.search.data.dto

data class SearchDto(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: SearchResult
)