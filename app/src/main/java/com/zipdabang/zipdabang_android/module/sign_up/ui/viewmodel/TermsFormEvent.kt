package com.zipdabang.zipdabang_android.module.sign_up.ui.viewmodel

sealed class TermsFormEvent{
    data class AllAgreeChanged(val checked : Boolean) : TermsFormEvent()
    data class RequiredOneChanged(val checked : Boolean) : TermsFormEvent()
    data class RequiredTwoChanged(val checked : Boolean) : TermsFormEvent()
    data class RequiredThreeChanged(val checked : Boolean) : TermsFormEvent()
    data class RequiredFourChanged(val checked : Boolean) : TermsFormEvent()
    data class ChoiceChanged(val checked : Boolean) : TermsFormEvent()
    data class BtnChanged(val enabled: Boolean) : TermsFormEvent()
}
