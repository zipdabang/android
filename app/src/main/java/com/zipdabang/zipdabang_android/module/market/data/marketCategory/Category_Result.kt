package com.zipdabang.zipdabang_android.module.market.data.marketCategory


data class Category_Result(
    val currentPageElements: Int,
    val isFirst: Boolean,
    val isLast: Boolean,
    val productList: List<Category_Product>,
    val totalElements: Int,
    val totalPage: Int
)