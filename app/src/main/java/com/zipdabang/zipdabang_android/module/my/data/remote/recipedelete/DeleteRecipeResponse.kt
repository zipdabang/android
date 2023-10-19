package com.zipdabang.zipdabang_android.module.my.data.remote.recipedelete

data class DeleteRecipeResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: String?
)