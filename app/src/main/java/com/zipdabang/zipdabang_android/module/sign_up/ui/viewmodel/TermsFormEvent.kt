package com.zipdabang.zipdabang_android.module.sign_up.ui.viewmodel

sealed class TermsFormEvent{
    data class AllagreeChecked(val allagree : Boolean) : TermsFormEvent()
    data class RequiredOneChecked(val requiredOne : Boolean) : TermsFormEvent()
    data class RequiredTwoChecked(val requiredTwo : Boolean) : TermsFormEvent()
    data class RequiredThreeChecked(val requiredThree : Boolean) : TermsFormEvent()
    data class RequiredFourChecked(val requiredFour : Boolean) : TermsFormEvent()
    data class ChoiceChecked(val choice : Boolean ): TermsFormEvent()
    object Submit : TermsFormEvent()
}
