package com.zipdabang.zipdabang_android.module.drawer.data.remote.reporterror

data class reportDto(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: reportResult
)

data class reportResult(val s : String)