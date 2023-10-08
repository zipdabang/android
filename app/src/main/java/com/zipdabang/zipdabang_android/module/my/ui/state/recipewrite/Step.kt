package com.zipdabang.zipdabang_android.module.my.ui.state.recipewrite


data class Step(
    val stepImage : Any? = null,
    val description : String = "",
    val stepWordCount : Int = 0,
    val textfieldEnabled : Boolean = true,
    val completeBtnEnabled : Boolean = true,
    val completeBtnVisible : Boolean = false,
)
