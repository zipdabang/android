package com.zipdabang.zipdabang_android.module.drawer.ui.state.userinfo

sealed class UserInfoPreferencesEvent{
    data class BeverageCheckListChanged(val index : Int, val checked : Boolean) : UserInfoPreferencesEvent()
    data class BtnEnabled(val enabled: Boolean) : UserInfoPreferencesEvent()
}
