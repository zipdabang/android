package com.zipdabang.zipdabang_android.entity.recipe

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zipdabang.zipdabang_android.common.Constants
import java.util.UUID

@Entity(tableName = Constants.RECIPE_COMMENT_ITEM_TABLE)
data class RecipeCommentsEntity(
    @PrimaryKey(autoGenerate = false)
    val itemId: String = UUID.randomUUID().toString(),
    val content: String,
    val createdAt: String,
    val isOwner: Boolean,
    val ownerImage: String,
    val ownerNickname: String,
    val commentId: Int,
    val ownerId: Int,
    val updatedAt: String
)
