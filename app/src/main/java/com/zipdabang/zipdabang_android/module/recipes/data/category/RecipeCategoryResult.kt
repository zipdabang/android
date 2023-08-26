package com.zipdabang.zipdabang_android.module.recipes.data.category

import kotlinx.serialization.Serializable

@Serializable
data class RecipeCategoryResult(
    val beverageCategoryList: List<BeverageCategory>,
    val size: Int
)