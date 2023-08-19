package com.zipdabang.zipdabang_android.module.sign_up.ui.viewmodel

import com.zipdabang.zipdabang_android.module.sign_up.data.remote.Terms

data class TermsListState(
    val isLoading : Boolean = false,
    val termsList : List<Terms> = emptyList(),
    val size : Int = 0,
    val error : String = ""
)
