package com.zipdabang.zipdabang_android.module.my.ui.state.myrecipe.write

sealed class RecipeWriteDialogEvent{
    data class RecipeDeleteChanged(val isOpen : Boolean) : RecipeWriteDialogEvent()
    data class TempRecipeDeleteChanged(val isOpen : Boolean) : RecipeWriteDialogEvent()
    data class CompleteRecipeDeleteChanged(val isOpen : Boolean) : RecipeWriteDialogEvent()

    data class FileSelectChanged(val isOpen : Boolean) : RecipeWriteDialogEvent()
    data class StepFileSelectChanged(val isOpen : Boolean, val stepNum : Int) : RecipeWriteDialogEvent()

    data class PermissionChanged(val isOpen : Boolean) : RecipeWriteDialogEvent()
    data class SaveChanged(val isOpen : Boolean) : RecipeWriteDialogEvent()
    data class UploadCategoryChanged(val isOpen : Boolean) : RecipeWriteDialogEvent()
    data class UploadCompleteChanged(val isOpen : Boolean) : RecipeWriteDialogEvent()
}

