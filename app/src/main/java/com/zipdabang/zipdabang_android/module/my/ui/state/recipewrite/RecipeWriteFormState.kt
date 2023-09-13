package com.zipdabang.zipdabang_android.module.my.ui.state.recipewrite

data class RecipeWriteFormState(
    val isLoading : Boolean = false,

    val thumbnail : String = "",
    val title : String ="",
    val titleWordCount : Int = 0,
    val time : String = "",
    val timeWordCount : Int = 0,
    val intro : String = "",
    val introWordCount : Int = 0,
    val ingredientCount : Int = 0,
    val ingredients : Ingredient,
    val stepCount : Int = 0,
    val steps : Step,
    val recipeTip : String = "",
    val recipeTipWordCount : Int = 0,
    val stepImages : List<String> = emptyList(),
    val categoryId : List<Int> = emptyList(),

    val error : String = ""
)
