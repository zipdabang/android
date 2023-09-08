package com.zipdabang.zipdabang_android.module.recipes.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zipdabang.zipdabang_android.common.Constants

@Entity(tableName = Constants.RECIPE_ITEM_TABLE)
data class RecipeItemEntity(
    @PrimaryKey(autoGenerate = true)
    val itemId: Int = 0,
    val categoryId: List<Int>,
    val comments: Int?,
    val createdAt: String,
    var isLiked: Boolean,
    var isScrapped: Boolean,
    var likes: Int,
    val nickname: String,
    val recipeId: Int,
    val recipeName: String,
    var scraps: Int,
    val thumbnailUrl: String
)
