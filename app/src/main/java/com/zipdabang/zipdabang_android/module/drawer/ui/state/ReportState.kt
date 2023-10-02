package com.zipdabang.zipdabang_android.module.drawer.ui.state

data class ReportState(
    val isSuccess : Boolean = false,
    val isLoading : Boolean = false,
    val isError : Boolean = false,
    val error : String = ""
)
