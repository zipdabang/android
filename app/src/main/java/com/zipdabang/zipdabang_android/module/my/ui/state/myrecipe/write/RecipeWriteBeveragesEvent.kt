package com.zipdabang.zipdabang_android.module.my.ui.state.myrecipe.write

sealed class RecipeWriteBeveragesEvent{
    data class StepFileSelectChanged(val index : Int, val clicked : Boolean) : RecipeWriteBeveragesEvent()
    data class BtnEnabled(val isEnabled : Boolean) : RecipeWriteBeveragesEvent()
}
