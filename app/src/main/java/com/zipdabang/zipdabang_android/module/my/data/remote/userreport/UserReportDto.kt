package com.zipdabang.zipdabang_android.module.my.data.remote.userreport

data class UserReportDto(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: UserReportResult
)