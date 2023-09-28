package com.zipdabang.zipdabang_android.module.my.ui.state.recipewrite

sealed class RecipeWriteDialogEvent{
    data class RecipeDeleteChanged(val isOpen : Boolean) : RecipeWriteDialogEvent()
    data class FileSelectChanged(val isOpen : Boolean) : RecipeWriteDialogEvent()
    data class PermissionChanged(val isOpen : Boolean) : RecipeWriteDialogEvent()
    data class SaveChanged(val isOpen : Boolean) : RecipeWriteDialogEvent()
    data class UploadCategoryChanged(val isOpen : Boolean) : RecipeWriteDialogEvent()
    data class UploadCompleteChanged(val isOpen : Boolean) : RecipeWriteDialogEvent()
}

