package com.zipdabang.zipdabang_android.module.recipes.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zipdabang.zipdabang_android.module.item.recipe.ui.RecipeCategoryLoading
import com.zipdabang.zipdabang_android.module.recipes.data.category.BeverageCategory
import com.zipdabang.zipdabang_android.module.recipes.ui.state.RecipeCategoryState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecipeCategoryList(
    categoryState: RecipeCategoryState,
    onCategoryClick: (Int) -> Unit
) {
    FlowRow(
        maxItemsInEachRow = 4,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        val categoryItems = categoryState.recipeCategories

        if (categoryState.isLoading) {
            repeat(8) {
                RecipeCategoryLoading()
            }
            /*            items(count = 6) {
                            RecipeCategoryLoading()
                        }*/
        } else {
            categoryItems?.let { categoryList ->
                categoryList.forEach {
                    com.zipdabang.zipdabang_android.module.item.recipe.ui.RecipeCategory(
                        categoryTitle = it.categoryName,
                        categoryId = it.id,
                        imageUrl = it.imageUrl ?: "null",
                        onClick = onCategoryClick
                    )
                }
            }
        }
        if (categoryState.errorMessage != null) {

        }
    }


}