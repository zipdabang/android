package com.zipdabang.zipdabang_android.module.sign_up.ui.state

import com.zipdabang.zipdabang_android.module.sign_up.data.remote.Terms

data class TermsFormState(
    val isLoading : Boolean = false,

    val termsList : List<Terms> = emptyList(),
    val size : Int = 0,

    val allAgree : Boolean = true,

    val requiredOne : Boolean = true,
    val requiredOneTitle : String = "",
    val requiredOneBody : String = "",
    val isMoreToSeeRequiredOne : Boolean = false,

    val requiredTwo : Boolean = true,
    val requiredTwoTitle : String = "",
    val requiredTwoBody : String = "",
    val isMoreToSeeRequiredTwo : Boolean = true,

    val requiredThree : Boolean = true,
    val requiredThreeTitle : String = "",
    val requiredThreeBody : String = "",
    val isMoreToSeeRequiredThree : Boolean = true,


    val requiredFour : Boolean = true,
    val requiredFourTitle : String = "",
    val requiredFourBody : String = "",
    val isMoreToSeeRequiredFour : Boolean = true,

    val choiceId : Int = 0,
    val choice : Boolean = true,
    val choiceTitle : String = "",
    val choiceBody : String = "",
    val isMoreToSeeChoice : Boolean = true,

    val btnEnabled : Boolean = true,

    val error : String = "",
)
