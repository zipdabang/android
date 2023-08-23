package com.zipdabang.zipdabang_android.module.recipes.data.preference

import kotlinx.serialization.Serializable

@Serializable
data class PreferenceResultDto(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: PreferenceToggleResult?
)