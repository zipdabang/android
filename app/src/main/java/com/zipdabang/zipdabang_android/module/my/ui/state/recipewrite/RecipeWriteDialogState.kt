package com.zipdabang.zipdabang_android.module.my.ui.state.recipewrite

data class RecipeWriteDialogState(
    val isOpenRecipeDelete : Boolean = false,
    val isOpenFileSelect : Boolean = false,
    val isOpenStepFileSelect : Boolean = false,
    val stepNum : Int = 1,
    val isOpenPermission : Boolean = false,
    val isOpenSave : Boolean = false,
    val isOpenUploadCategory : Boolean = false,
    val isOpenUploadComplete : Boolean = false,
)
