package com.zipdabang.zipdabang_android.module.my.data.remote.recipeedit.temp

data class PostTempRecipeRequestBody(
    val content: Content,
    val stepImages: List<String>,
    val thumbnail: String
)