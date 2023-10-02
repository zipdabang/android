package com.zipdabang.zipdabang_android.module.comment.ui

data class BlockUserState(
    val isLoading: Boolean = false,
    val isConnectionSuccessful: Boolean? = null,
    val errorMessage: String? = null,
    val isBlockSuccessful: Boolean? = null
)
