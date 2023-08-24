package com.zipdabang.zipdabang_android.module.market.ui

import com.zipdabang.zipdabang_android.module.market.data.marketMain.Product
import com.zipdabang.zipdabang_android.module.market.data.marketMain.ProductCategory

data class MainMarketState(
    val isLoading : Boolean = false,
    val bannerList : List<String> = emptyList(),
    val categoryList : List<ProductCategory> = emptyList(),
    val recentProductList : List<Product> = emptyList(),
    val error : String = ""
)
