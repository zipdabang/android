package com.zipdabang.zipdabang_android.module.sign_up.ui.viewmodel

import com.zipdabang.zipdabang_android.R

data class RegistrationFormState(
    val terms: Boolean = true,


    val name: String? = "",
    val birthday: Int? = null,
    val gender: Boolean? = null,
    val phoneNumber: Int? = null,
    val phoneNumberLabel: String = R.string.signup_phonenumber_notcertificate.toString(),
    val zipCode: Int? = null,
    val address: String? = "",
    val detailAddress: String? = "",


    val nickname: String = "",
    val nicknameLabel: String = "",


    val preferences: List<Boolean> = emptyList<Boolean>(),
)
