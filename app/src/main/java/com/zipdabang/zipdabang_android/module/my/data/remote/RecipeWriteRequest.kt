package com.zipdabang.zipdabang_android.module.my.data.remote

data class RecipeWriteRequest(
    val content: RecipeWriteContent,
    val stepImages: List<String>,
    val thumbnail: String
)