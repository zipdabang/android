package com.zipdabang.zipdabang_android.module.sign_up.ui.viewmodel

data class UserAddressFormState(
    val isLoading : Boolean = false,

    val zipCode : String = "",
    val address : String = "",
    val detailAddress : String = "",
    val btnEnabled : Boolean = false,

    val error : String = ""
)
