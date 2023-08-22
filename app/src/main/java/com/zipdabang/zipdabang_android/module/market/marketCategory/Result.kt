package com.zipdabang.zipdabang_android.module.market.marketCategory

import androidx.room.Entity



data class Result(
    val currentPageElements: Int,
    val isFirst: Boolean,
    val isLast: Boolean,
    val productList: List<Product>,
    val totalElements: Int,
    val totalPage: Int
)