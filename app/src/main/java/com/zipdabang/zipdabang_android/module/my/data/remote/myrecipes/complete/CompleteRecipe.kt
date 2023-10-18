package com.zipdabang.zipdabang_android.module.my.data.remote.myrecipes.complete

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zipdabang.zipdabang_android.common.Constants

@Entity(tableName = Constants.MY_COMPLETE_RECIPES_TABLE)
data class CompleteRecipe(
    @PrimaryKey(autoGenerate = false)
    val recipeId: Int,
    val categoryId: List<Int>,
    val recipeName: String,
    val nickname: String,
    val thumbnailUrl: String,
    val createdAt: String,
    val updatedAt: String,
    val likes: Int,
    val comments: Int,
    val scraps: Int,
    val isLiked: Boolean,
    val isScrapped: Boolean
)