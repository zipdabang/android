package com.zipdabang.zipdabang_android.module.my.ui.state.recipewrite

import com.zipdabang.zipdabang_android.module.drawer.ui.state.userinfo.UserInfoBasicEvent

sealed class RecipeWriteFormEvent{
    data class ThumbnailChanged(val thumbnail : Any?) : RecipeWriteFormEvent()
    data class ThumbnailChangedToNull(val thumbnail : Any?) : RecipeWriteFormEvent()

    data class TitleChanged(val title : String) : RecipeWriteFormEvent()
    data class TimeChanged(val time : String) : RecipeWriteFormEvent()
    data class IntroChanged(val intro : String) : RecipeWriteFormEvent()
    data class RecipeTipChanged(val recipeTip : String) : RecipeWriteFormEvent()

    data class BtnIngredientAdd(val ingredientNum : Int) : RecipeWriteFormEvent()
    data class BtnIngredientDelete(val ingredientNum: Int) : RecipeWriteFormEvent()
    data class BtnIngredientAddEnabled(val ingredientNum: Int) : RecipeWriteFormEvent()
    data class IngredientChanged(val ingredientName : String, val ingredientNum: Int) : RecipeWriteFormEvent()
    data class QuantityChanged(val quantityName : String, val quantityNum: Int) : RecipeWriteFormEvent()

    data class BtnStepAdd(val stepNum : Int) : RecipeWriteFormEvent()
    data class BtnStepComplete(val stepNum : Int) : RecipeWriteFormEvent()
    data class BtnStepDelete(val stepNum: Int) : RecipeWriteFormEvent()
    data class BtnStepEdit(val stepNum : Int) : RecipeWriteFormEvent()
    data class StepChanged(val stepDescription : String, val stepNum: Int) : RecipeWriteFormEvent()
    data class StepIsValidate(val stepNum : Int) : RecipeWriteFormEvent()
    data class StepImageChanged(val stepImage : Any?, val stepNum : Int) : RecipeWriteFormEvent()
    data class StepImageChangedToEmpty(val stepImage : Any? ,val stepNum : Int) : RecipeWriteFormEvent()


    data class BtnEnabled(val enabled: Boolean) : RecipeWriteFormEvent()

}
