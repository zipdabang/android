package com.zipdabang.zipdabang_android.module.my.ui.state

import com.zipdabang.zipdabang_android.module.my.data.remote.otherinfo.OtherRecipe

data class OtherRecipePreviewState(
    val recipeList : List<OtherRecipe> = emptyList(),
    val isLoading : Boolean= false,
    val isSuccess : Boolean= false,
    val isError : Boolean = false
)
