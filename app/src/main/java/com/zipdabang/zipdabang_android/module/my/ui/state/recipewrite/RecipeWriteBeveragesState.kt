package com.zipdabang.zipdabang_android.module.my.ui.state.recipewrite

import com.zipdabang.zipdabang_android.module.my.data.remote.recipewrite.BeverageCategory

data class RecipeWriteBeveragesState(
    val isLoading : Boolean = false,

    val beverageList : List<BeverageCategory> = emptyList(),
    val beverageCheckList : List<Boolean> = emptyList(),
    val size : Int = 0,
    val btnEnabled : Boolean = false,

    val error : String = ""
)
