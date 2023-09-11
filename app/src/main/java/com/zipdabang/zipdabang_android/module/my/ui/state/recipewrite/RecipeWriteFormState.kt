package com.zipdabang.zipdabang_android.module.my.ui.state.recipewrite

data class RecipeWriteFormState(
    val isLoading : Boolean = false,

    val title : String ="",
    val time : String = "",
    val intro : String = "",
    val ingredientCount : Int = 0,
    val ingredients : Ingredient,
    val stepCount : Int = 0,
    val steps : Step,
    val categoryId : List<Int> = emptyList(),
    val recipeTip : String = "",
    val thumbnail : String = "",
    val stepImages : List<String> = emptyList(),

    val error : String = ""
)
