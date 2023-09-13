package com.zipdabang.zipdabang_android.module.comment.ui

data class EditCommentState(
    val isLoading: Boolean = false,
    val isConnectionSuccessful: Boolean? = null,
    val errorMessage: String? = null,
    val isEditSuccessful: Boolean? = null
)
