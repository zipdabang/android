package com.zipdabang.zipdabang_android.module.my.data.remote.myrecipes.completewithimage

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zipdabang.zipdabang_android.common.Constants

@Entity(tableName = Constants.MY_COMPLETE_RECIPES_WITH_IMAGES_TABLE)
data class CompleteRecipeWithImg(
    val categoryId: List<Int>,
    val comments: Int,
    val createdAt: String,
    val isLiked: Boolean,
    val isScrapped: Boolean,
    val likes: Int,
    val nickname: String,
    @PrimaryKey(autoGenerate = false)
    val recipeId: Int,
    val recipeName: String,
    val scraps: Int,
    val thumbnailUrl: String,
    val updatedAt: String
)