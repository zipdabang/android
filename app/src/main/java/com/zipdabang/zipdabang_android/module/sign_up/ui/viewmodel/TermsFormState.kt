package com.zipdabang.zipdabang_android.module.sign_up.ui.viewmodel

import com.zipdabang.zipdabang_android.module.sign_up.data.remote.Terms

data class TermsFormState(
    val allAgree : Boolean = true,
    val requiredOne : Boolean = true,
    val requiredTwo : Boolean = true,
    val requiredThree : Boolean = true,
    val requiredFour : Boolean = true,
    val choice : Boolean = true,
    val btnEnabled : Boolean = true,
)
