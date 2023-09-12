package com.zipdabang.zipdabang_android.module.comment.ui

data class RecipeCommentState(
    val content: String,
    val createdAt: String,
    val isOwner: Boolean,
    val ownerImage: String,
    val ownerNickname: String,
    val commentId: Int,
    val ownerId: Int,
    val updatedAt: String
)
