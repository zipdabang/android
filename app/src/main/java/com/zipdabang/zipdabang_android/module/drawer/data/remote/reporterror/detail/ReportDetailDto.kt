package com.zipdabang.zipdabang_android.module.drawer.data.remote.reporterror.detail

data class ReportDetailDto(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: ReportDetailResult?
)