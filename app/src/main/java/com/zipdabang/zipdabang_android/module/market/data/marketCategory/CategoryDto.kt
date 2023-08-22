package com.zipdabang.zipdabang_android.module.market.data.marketCategory

import androidx.room.Entity
import com.zipdabang.zipdabang_android.common.Constants.PAGING3_DATABASE



data class CategoryDto(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: Category_Result
)