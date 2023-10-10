package com.zipdabang.zipdabang_android.module.drawer.data.remote.reporterror

data class ReportListDto(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: ReportListResult?
)