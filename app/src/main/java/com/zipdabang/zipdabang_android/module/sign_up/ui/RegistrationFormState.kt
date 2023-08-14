package com.zipdabang.zipdabang_android.module.sign_up.ui

import com.zipdabang.zipdabang_android.R

data class RegistrationFormState(
    val terms: Boolean? = true,


    val name: String? = "",
    val birthday: Int? = null,
    val gender: Boolean? = null,
    val phoneNumber: Int? = null,
    val phoneNumberLabel: Int = R.string.signup_phonenumber_notcertificate,
    val address: String? = "",
    val zipCode: String? = "",
    val detailAddress: String? = "",


    val nickname: String = "",
    val nicknameLabel: String = "",


    val preferences: List<Boolean> = emptyList<Boolean>(),
)
