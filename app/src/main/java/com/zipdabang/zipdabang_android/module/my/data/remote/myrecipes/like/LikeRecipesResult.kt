package com.zipdabang.zipdabang_android.module.my.data.remote.myrecipes.like

import androidx.room.Entity
import com.zipdabang.zipdabang_android.common.Constants



data class LikeRecipesResult(
    val currentPageElements: Int,
    val isFirst: Boolean,
    val isLast: Boolean,
    val recipeList: List<LikeRecipe>,
    val totalElements: Int,
    val totalPage: Int
)