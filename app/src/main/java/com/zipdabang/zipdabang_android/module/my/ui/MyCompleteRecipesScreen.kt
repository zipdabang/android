package com.zipdabang.zipdabang_android.module.my.ui

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.zipdabang.zipdabang_android.module.my.ui.component.myrecipes.CompleteRecipeItem
import com.zipdabang.zipdabang_android.module.my.ui.viewmodel.MyRecipesViewModel

@Composable
fun MyCompleteRecipesScreen(
    shimmering: Boolean,
    onClickCompleteRecipes : (Int)->Unit,
    viewModel : MyRecipesViewModel = hiltViewModel()
) {
    val completeRecipeItems = viewModel.completeRecipeItems.collectAsLazyPagingItems()

    LaunchedEffect(key1 = true ){
        viewModel.getCompleteRecipeItems()
    }
    //Log.e("my_completerecipes_itemCount", completeRecipeItems.itemCount.toString())

    LazyColumn(
        modifier = Modifier.padding(16.dp)
            .fillMaxSize()
    ){
        items(completeRecipeItems.itemCount){
            CompleteRecipeItem(
                thumbnail = completeRecipeItems[it]!!.thumbnailUrl,
                title = completeRecipeItems[it]!!.recipeName,
                createdAt = completeRecipeItems[it]!!.createdAt,
                onClick = {
                    onClickCompleteRecipes(completeRecipeItems[it]!!.recipeId)
                }
            )
        }
    }

}