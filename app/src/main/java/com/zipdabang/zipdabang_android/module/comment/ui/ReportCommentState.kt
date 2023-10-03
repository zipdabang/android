package com.zipdabang.zipdabang_android.module.comment.ui

data class ReportCommentState(
    val isLoading: Boolean = false,
    val isConnectionSuccessful: Boolean? = null,
    val errorMessage: String? = null,
    val isReportSuccessful: Boolean? = null
)
