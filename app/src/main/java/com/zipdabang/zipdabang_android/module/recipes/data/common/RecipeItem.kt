package com.zipdabang.zipdabang_android.module.recipes.data.common

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zipdabang.zipdabang_android.common.Constants.RECIPE_ITEM_TABLE
import kotlinx.serialization.Serializable

@Entity(tableName = RECIPE_ITEM_TABLE)
@Serializable
data class RecipeItem(
    val categoryId: List<Int>,
    val comments: Int,
    val createdAt: String,
    var isLiked: Boolean,
    var isScrapped: Boolean,
    var likes: Int,
    val owner: String,
    @PrimaryKey(autoGenerate = false)
    val recipeId: Int,
    val recipeName: String,
    var scraps: Int,
    val thumbnailUrl: String
)