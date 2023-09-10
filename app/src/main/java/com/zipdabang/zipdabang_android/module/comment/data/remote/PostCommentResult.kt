package com.zipdabang.zipdabang_android.module.comment.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class PostCommentResult(
    val content: String,
    val createdAt: String,
    val isOwner: Boolean,
    val ownerImage: String,
    val ownerNickname: String
)