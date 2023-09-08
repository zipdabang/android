package com.zipdabang.zipdabang_android.module.comment.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class RecipeCommentDto(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: RecipeCommentResult?
)