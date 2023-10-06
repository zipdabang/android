package com.zipdabang.zipdabang_android.entity.recipe

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zipdabang.zipdabang_android.common.Constants
import java.util.UUID

@Entity(tableName = Constants.RECIPE_ITEM_TEA_TABLE)
data class RecipeTeaEntity(
    @PrimaryKey(autoGenerate = false)
    val itemId: String = UUID.randomUUID().toString(),
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
