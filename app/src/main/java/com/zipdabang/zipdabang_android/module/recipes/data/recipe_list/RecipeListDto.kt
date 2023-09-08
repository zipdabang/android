package com.zipdabang.zipdabang_android.module.recipes.data.recipe_list

import androidx.room.Entity
import kotlinx.serialization.Serializable

@Serializable
data class RecipeListDto(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: RecipeListResult?
)