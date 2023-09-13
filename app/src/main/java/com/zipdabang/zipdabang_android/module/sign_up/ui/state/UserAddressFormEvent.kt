package com.zipdabang.zipdabang_android.module.sign_up.ui.state

sealed class UserAddressFormEvent{
    data class ZipcodeChanged(val zipCode : String) : UserAddressFormEvent()
    data class ZipcodeClicked(val cliked : Boolean) : UserAddressFormEvent()
    data class AddressChanged(val address : String) : UserAddressFormEvent()
    data class DetailaddressChanged(val detailAddress : String) : UserAddressFormEvent()
    data class BtnChanged(val enabled: Boolean) : UserAddressFormEvent()

}
