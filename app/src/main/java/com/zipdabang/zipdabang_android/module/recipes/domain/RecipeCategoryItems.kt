package com.zipdabang.zipdabang_android.module.recipes.domain

import com.zipdabang.zipdabang_android.module.recipes.data.category.BeverageCategory
import java.io.Serializable

data class RecipeCategoryItems(
    val code: Int,
    val isSuccessful: Boolean,
    val message: String,
    val categoryList: List<BeverageCategory>?
): Serializable