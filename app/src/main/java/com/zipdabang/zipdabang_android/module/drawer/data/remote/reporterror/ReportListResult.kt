package com.zipdabang.zipdabang_android.module.drawer.data.remote.reporterror

data class ReportListResult(
    val currentPageElements: Int,
    val inqueryList: List<Inquery>,
    val isFirst: Boolean,
    val isLast: Boolean,
    val totalElements: Int,
    val totalPage: Int
)