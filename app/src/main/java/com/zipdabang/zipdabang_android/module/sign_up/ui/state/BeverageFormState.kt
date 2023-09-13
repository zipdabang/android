package com.zipdabang.zipdabang_android.module.sign_up.ui.state

import com.zipdabang.zipdabang_android.module.sign_up.data.remote.BeverageCategory

data class BeverageFormState(
    val isLoading : Boolean = false,

    val beverageList : List<BeverageCategory> = emptyList(),
    val beverageCheckList : List<Boolean> = emptyList(),
    val size : Int = 0,
    val btnEnabled : Boolean = false,

    val error : String = ""
)
