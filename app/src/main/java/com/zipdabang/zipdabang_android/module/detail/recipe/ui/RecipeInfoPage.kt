package com.zipdabang.zipdabang_android.module.detail.recipe.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RecipeInfoPage(
    recipeDetailState: RecipeDetailState,
    onClickCart: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        
        RecipeIngredients(
            ingredients = recipeDetailState.recipeDetailData?.recipeIngredients ?: emptyList(),
            onClickCart = onClickCart
        )

        Divider(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 20.dp)
        )

        RecipeSteps(
            steps = recipeDetailState.recipeDetailData?.recipeSteps ?: emptyList()
        )

        Spacer(modifier = Modifier
            .height(20.dp)
        )

        RecipeTip(recipeTip = recipeDetailState.recipeDetailData?.recipeInfo?.recipeTip ?: "")
    }
}