package com.zipdabang.zipdabang_android.module.detail.recipe.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zipdabang.zipdabang_android.module.detail.recipe.data.Ingredient

@Composable
fun RecipeIngredients(
    ingredients: List<Ingredient> = emptyList()
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "재료",

        )
    }
}

@Composable
fun IngredientItem(
    ingredient: Ingredient
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {

    }
}