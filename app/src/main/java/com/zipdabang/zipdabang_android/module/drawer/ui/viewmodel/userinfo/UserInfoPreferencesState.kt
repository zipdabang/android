package com.zipdabang.zipdabang_android.module.drawer.ui.viewmodel.userinfo

import com.zipdabang.zipdabang_android.module.sign_up.data.remote.BeverageCategory

data class UserInfoPreferencesState(
    val isLoading : Boolean = false,

    val beverageList : List<BeverageCategory> = emptyList(),
    val beverageCheckList : List<Boolean> = emptyList(),
    val size : Int = 0,
    val btnEnabled : Boolean = false,

    val error : String = ""
)
