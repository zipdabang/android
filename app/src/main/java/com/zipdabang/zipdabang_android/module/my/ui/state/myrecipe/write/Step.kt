package com.zipdabang.zipdabang_android.module.my.ui.state.myrecipe.write


data class Step(
    val stepImage : Any? = null,
    val description : String = "",
    val stepWordCount : Int = 0,
    val completeBtnEnabled : Boolean = true,
    val completeBtnVisible : Boolean = false,
    val addBtnVisible : Boolean = false,
)
