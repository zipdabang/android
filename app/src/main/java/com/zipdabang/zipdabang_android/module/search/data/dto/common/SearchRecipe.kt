package com.zipdabang.zipdabang_android.module.search.data.dto.common

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zipdabang.zipdabang_android.common.Constants

data class SearchRecipe(
    val categoryId: List<Int>,
    val comments: Int,
    val createdAt: String,
    val updatedAt : String,
    var isLiked: Boolean,
    var isScrapped: Boolean,
    var likes: Int,
    val nickname: String,
    val recipeId: Int,
    val recipeName: String,
    val scraps: Int,
    val thumbnailUrl: String
)


