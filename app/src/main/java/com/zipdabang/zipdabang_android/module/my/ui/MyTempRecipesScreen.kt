package com.zipdabang.zipdabang_android.module.my.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.zipdabang.zipdabang_android.module.my.ui.component.myrecipes.TempRecipeItem
import com.zipdabang.zipdabang_android.module.my.ui.viewmodel.MyRecipesViewModel

@Composable
fun MyTempRecipesScreen(
    shimmering: Boolean,
    onClickTempRecipes : (Int)->Unit,
    viewModel : MyRecipesViewModel = hiltViewModel()
) {
    val tempRecipeItems = viewModel.tempRecipeItems.collectAsLazyPagingItems()

    LaunchedEffect(key1 = true ){
        viewModel.getTempRecipeItems()
    }

    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ){
        items(tempRecipeItems.itemCount){
            TempRecipeItem(
                thumbnail = tempRecipeItems[it]!!.thumbnailUrl,
                title =tempRecipeItems[it]!!.recipeName,
                createdAt =tempRecipeItems[it]!!.updatedAt,
                onClick = {
                    onClickTempRecipes(tempRecipeItems[it]!!.tempId)
                }
            )
        }
    }

}