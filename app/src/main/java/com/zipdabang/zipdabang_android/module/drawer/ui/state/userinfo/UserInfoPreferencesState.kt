package com.zipdabang.zipdabang_android.module.drawer.ui.state.userinfo

import com.zipdabang.zipdabang_android.module.sign_up.data.remote.BeverageCategory

data class UserInfoPreferencesState(
    val isLoading : Boolean = false,

    val preferBeverageList : List<BeverageCategory> = emptyList(),
    val preferBeverageCheckList : List<Boolean> = emptyList(),
    val size : Int = 0,
    val btnEnabled : Boolean = false,

    val error : String = ""
)
