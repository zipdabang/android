package com.zipdabang.zipdabang_android.module.market.data.marketCategory

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zipdabang.zipdabang_android.common.Constants.PAGING3_DATABASE


@Entity(tableName = PAGING3_DATABASE)
data class Category_Product(
    val isInBasket: Boolean,
    val isLiked: Boolean,
    val price: String,
    val productCategoryId: Int,
    @PrimaryKey(autoGenerate = false)
    val productId: Int,
    val productImageUrl: String,
    val productName: String,
    val productScore: Int
)