package com.zipdabang.zipdabang_android.module.drawer.ui.viewmodel.userinfo

import com.zipdabang.zipdabang_android.module.sign_up.ui.viewmodel.BeverageFormEvent

sealed class UserInfoPreferencesEvent{
    data class BeverageCheckListChanged(val index : Int, val checked : Boolean) : UserInfoPreferencesEvent()
    data class BtnEnabled(val enabled: Boolean) : UserInfoPreferencesEvent()
}
