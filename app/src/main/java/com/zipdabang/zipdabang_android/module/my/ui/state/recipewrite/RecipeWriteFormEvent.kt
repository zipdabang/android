package com.zipdabang.zipdabang_android.module.my.ui.state.recipewrite

import com.zipdabang.zipdabang_android.module.drawer.ui.state.userinfo.UserInfoBasicEvent

sealed class RecipeWriteFormEvent{
    data class ThumbnailChanged(val thumbnail : Any?) : RecipeWriteFormEvent()
    data class TitleChanged(val title : String) : RecipeWriteFormEvent()
    data class TimeChanged(val time : String) : RecipeWriteFormEvent()
    data class IntroChanged(val intro : String) : RecipeWriteFormEvent()
    data class RecipeTipChanged(val recipeTip : String) : RecipeWriteFormEvent()
    data class BtnIngredientAdd(val ingredientNum : Int) : RecipeWriteFormEvent()
    data class IngredientChanged(val ingredient : Ingredient, val ingredientNum: Int) : RecipeWriteFormEvent()
    data class BtnStepAdd(val stepNum : Int) : RecipeWriteFormEvent()
    data class StepChanged(val step : Step, val stepNum: Int) : RecipeWriteFormEvent()


    data class BtnEnabled(val enabled: Boolean) : RecipeWriteFormEvent()

}
