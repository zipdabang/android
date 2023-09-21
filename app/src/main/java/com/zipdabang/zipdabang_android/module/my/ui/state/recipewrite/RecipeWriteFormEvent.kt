package com.zipdabang.zipdabang_android.module.my.ui.state.recipewrite

import com.zipdabang.zipdabang_android.module.drawer.ui.state.userinfo.UserInfoBasicEvent

sealed class RecipeWriteFormEvent{
    data class titleChanged(val title : String) : RecipeWriteFormEvent()
    //data class titleWordCountChanged(val titleWordCount : Int) : RecipeWriteFormEvent()
    data class timeChanged(val time : String) : RecipeWriteFormEvent()
    //data class timeWordCountChanged(val timeWordCount : Int) : RecipeWriteFormEvent()
    data class introChanged(val intro : String) : RecipeWriteFormEvent()
    //data class introWordCountChanged(val introWordCount : Int) : RecipeWriteFormEvent()
    data class recipeTipChanged(val recipeTip : String) : RecipeWriteFormEvent()
    //data class recipeTipWordCountChanged(val recipeTipWordCount : Int) : RecipeWriteFormEvent()
    data class BtnEnabled(val enabled: Boolean) : RecipeWriteFormEvent()

}
