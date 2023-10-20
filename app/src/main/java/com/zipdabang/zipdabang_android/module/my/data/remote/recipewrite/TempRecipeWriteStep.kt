package com.zipdabang.zipdabang_android.module.my.data.remote.recipewrite

import okhttp3.MultipartBody

data class TempRecipeWriteStep(
    val description: String?,
    val stepNum: Int,
    val stepUrl: String?,
)