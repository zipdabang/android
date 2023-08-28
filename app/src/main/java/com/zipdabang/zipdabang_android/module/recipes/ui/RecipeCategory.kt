package com.zipdabang.zipdabang_android.module.recipes.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zipdabang.zipdabang_android.module.item.recipe.ui.RecipeCategoryLoading
import com.zipdabang.zipdabang_android.module.recipes.data.category.BeverageCategory
import com.zipdabang.zipdabang_android.module.recipes.ui.state.RecipeCategoryState

@Composable
fun RecipeCategoryList(
    categoryState: RecipeCategoryState,
    onCategoryClick: (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(
            all = 12.dp
        ),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        val categoryItems = categoryState.recipeCategories

        if (categoryState.isLoading) {
            items(count = 6) {
                RecipeCategoryLoading()
            }
        } else {
            categoryItems?.let { categoryList ->
                items(
                    items = categoryList
                ) {
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