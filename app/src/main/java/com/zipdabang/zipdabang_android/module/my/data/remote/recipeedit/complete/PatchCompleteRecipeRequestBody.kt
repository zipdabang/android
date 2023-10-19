package com.zipdabang.zipdabang_android.module.my.data.remote.recipeedit.complete

data class PatchCompleteRecipeRequestBody(
    val content: Content,
    val stepImages: List<String>,
    val thumbnail: String
)