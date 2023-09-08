package com.zipdabang.zipdabang_android.module.comment.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class PostCommentContent(
    val comment: String
)