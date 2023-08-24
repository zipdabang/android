package com.zipdabang.zipdabang_android.module.sign_up.ui.viewmodel

import com.zipdabang.zipdabang_android.module.sign_up.data.remote.BeverageCategory

data class BeveragesListState(
    val isLoading : Boolean = false,
    val beverageList : List<BeverageCategory> = emptyList(),
    val size : Int = 0,
    val error : String = ""
)
