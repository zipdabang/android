package com.zipdabang.zipdabang_android.module.market.data.marketMain

data class Result(
    val categoryListSize: Int,
    val productCategoryList: List<ProductCategory>,
    val productList: List<Product>,
    val productListSize: Int
)