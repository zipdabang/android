package com.zipdabang.zipdabang_android.module.my.data.remote.myrecipes.like

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zipdabang.zipdabang_android.common.Constants

@Entity(tableName = Constants.MY_LIKED_RECIPES_TABLE)
data class LikeRecipe(
    val categoryId: List<Int>,
    val comments: Int,
    val createdAt: String,
    var isLiked: Boolean,
    var isScrapped: Boolean,
    var likes: Int,
    val nickname: String,
    @PrimaryKey(autoGenerate = false)
    val recipeId: Int,
    val recipeName: String,
    val scraps: Int,
    val thumbnailUrl: String,
    val updatedAt: String
)