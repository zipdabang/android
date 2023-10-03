package com.zipdabang.zipdabang_android.module.detail.recipe.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zipdabang.zipdabang_android.module.detail.recipe.domain.RecipeDetailDomain
import com.zipdabang.zipdabang_android.ui.theme.DialogBackground

@Composable
fun RecipeInfoPage(
    recipeDetailState: RecipeDetailDomain?,
    // onClickCart: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Spacer(modifier = Modifier.height(20.dp))

            RecipeIngredients(
                ingredients = recipeDetailState?.recipeIngredients ?: emptyList()
            )

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 20.dp)
                    .height(1.dp),
                color = Color(0x1A262D31)
            )

            RecipeSteps(
                steps = recipeDetailState?.recipeSteps ?: emptyList()
            )

            Spacer(modifier = Modifier.height(20.dp))

            RecipeTip(recipeTip = recipeDetailState?.recipeInfo?.recipeTip ?: "")

            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}