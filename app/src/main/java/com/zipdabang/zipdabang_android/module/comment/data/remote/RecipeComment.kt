package com.zipdabang.zipdabang_android.module.comment.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class RecipeComment(
    val content: String,
    val createdAt: String,
    val isOwner: Boolean,
    val ownerImage: String,
    val ownerNickname: String,
    val commentId: Int,
    val ownerId: Int,
    val updatedAt: String
)