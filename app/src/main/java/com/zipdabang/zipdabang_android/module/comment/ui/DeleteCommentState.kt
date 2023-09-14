package com.zipdabang.zipdabang_android.module.comment.ui

data class DeleteCommentState(
    val isLoading: Boolean = false,
    val isConnectionSuccessful: Boolean? = null,
    val errorMessage: String? = null,
    val isDeleteSuccessful: Boolean? = null
)
