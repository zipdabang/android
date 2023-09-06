package com.zipdabang.zipdabang_android.module.drawer.ui.viewmodel.userinfostate

import com.zipdabang.zipdabang_android.module.sign_up.ui.viewmodel.UserAddressFormEvent

sealed class UserInfoDetailEvent{
    data class ZipcodeChanged(val zipCode : String) : UserInfoDetailEvent()
    data class ZipcodeClicked(val cliked : Boolean) : UserInfoDetailEvent()
    data class AddressChanged(val address : String) : UserInfoDetailEvent()
    data class DetailaddressChanged(val detailAddress : String) : UserInfoDetailEvent()
    data class BtnEnabled(val enabled: Boolean) : UserInfoDetailEvent()
}
