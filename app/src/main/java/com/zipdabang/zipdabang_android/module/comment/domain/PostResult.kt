package com.zipdabang.zipdabang_android.module.comment.domain

data class PostResult(
    val code: Int,
    val message: String?,
    val isConnectionSuccessful: Boolean,
    val isPostSuccessful: Boolean
)
