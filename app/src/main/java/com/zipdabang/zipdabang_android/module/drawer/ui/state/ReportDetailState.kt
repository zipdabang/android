package com.zipdabang.zipdabang_android.module.drawer.ui.state

import com.zipdabang.zipdabang_android.module.drawer.data.remote.reporterror.detail.ReportDetailDto
import com.zipdabang.zipdabang_android.module.drawer.data.remote.reporterror.detail.ReportDetailResult

data class ReportDetailState(
    val isSuccess : Boolean = false,
    val isError : Boolean = false,
    val isLoading : Boolean = false,
    val reportDetail : ReportDetailResult? = ReportDetailResult("", emptyList(),"","")
)
