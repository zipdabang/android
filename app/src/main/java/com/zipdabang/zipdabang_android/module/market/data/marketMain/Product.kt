package com.zipdabang.zipdabang_android.module.market.data.marketMain

data class Product(
    val isInBasket: Boolean,
    val isLiked: Boolean,
    val price: String,
    val productCategoryId: Int,
    val productId: Int,
    val productImageUrl: String,
    val productName: String,
    val productScore: Int
)