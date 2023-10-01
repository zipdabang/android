package com.zipdabang.zipdabang_android.module.drawer.data.remote.reporterror

data class ReportState(
    val isSuccess : Boolean = false,
    val isLoading : Boolean = false,
    val isError : Boolean = false,
    val error : String = ""
)
