package com.zipdabang.zipdabang_android.module.comment.ui

data class PostCommentState(
    val isLoading: Boolean = false,
    val isConnectionSuccessful: Boolean? = null,
    val errorMessage: String? = null,
    val isPostSuccessful: Boolean? = null
)
