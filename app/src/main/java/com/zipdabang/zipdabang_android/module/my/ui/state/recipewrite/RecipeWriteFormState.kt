package com.zipdabang.zipdabang_android.module.my.ui.state.recipewrite

data class RecipeWriteFormState(
    val isLoading : Boolean = false,

    val thumbnail : Any? = null,
    val title : String ="",
    val titleWordCount : Int = 0,
    val time : String = "",
    val timeWordCount : Int = 0,
    val intro : String = "",
    val introWordCount : Int = 0,
    val recipeTip : String = "",
    val recipeTipWordCount : Int = 0,
    val categoryId : List<Int> = emptyList(),

    val ingredients : List<Ingredient> = emptyList(),
    val ingredientsNum : Int = 1,
    val ingredientBtnEnabled : Boolean = false,

    val steps : List<Step> = emptyList(),
    val stepsNum : Int = 1,
    val stepBtnEnabled : Boolean = false,

    val btnEnabled : Boolean = true,

    val error : String = ""
)
