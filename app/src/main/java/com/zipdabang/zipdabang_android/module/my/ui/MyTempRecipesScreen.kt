package com.zipdabang.zipdabang_android.module.my.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.zipdabang.zipdabang_android.module.item.recipe.ui.RecipeCardLoading
import com.zipdabang.zipdabang_android.module.my.ui.component.myrecipes.TempRecipeItem
import com.zipdabang.zipdabang_android.module.my.ui.component.myrecipes.TempRecipeItemLoading
import com.zipdabang.zipdabang_android.module.my.ui.viewmodel.MyRecipesViewModel
import com.zipdabang.zipdabang_android.ui.component.CustomDialogType1
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun MyTempRecipesScreen(
    onClickTempRecipes : (Int)->Unit,
    viewModel : MyRecipesViewModel = hiltViewModel()
) {
    val tempRecipeItems = viewModel.tempRecipeItems.collectAsLazyPagingItems()
    val loadingState = rememberUpdatedState(tempRecipeItems.loadState)
    val showDialogSave = remember { mutableStateOf(false) }
    var tempId = remember { mutableStateOf(0) }


    LaunchedEffect(key1 = true ){
        viewModel.getTempRecipeItems()
    }

    if(loadingState.value.refresh is LoadState.Loading){
        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ){
            items(8) {
                TempRecipeItemLoading()
            }
        }
    }
    else if(tempRecipeItems.itemCount == 0){
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                textAlign = TextAlign.Center,
                text= "임시저장한 레시피가 없습니다.",
                style= ZipdabangandroidTheme.Typography.fourteen_300,
                color= ZipdabangandroidTheme.Colors.Typo
            )
        }
    }
    else{
        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ){
            items(tempRecipeItems.itemCount){
                TempRecipeItem(
                    thumbnail = tempRecipeItems[it]!!.thumbnailUrl,
                    title = if(tempRecipeItems[it]!!.recipeName == "") "제목을 지정하지 않았습니다."
                    else tempRecipeItems[it]!!.recipeName,
                    createdAt = tempRecipeItems[it]!!.updatedAt,
                    onDeleteClick = {
                        tempId.value = tempRecipeItems[it]!!.tempId
                        showDialogSave.value = true
                        Log.e("my-delete-temprecipe1",  "${tempId}")
                    },
                    onEditClick = {
                        onClickTempRecipes(tempRecipeItems[it]!!.tempId)
                    }
                )
            }
        }
    }

    if(showDialogSave.value){
        CustomDialogType1(
            title = "나의 레시피 삭제",
            text = "나의 레시피를 삭제하시겠습니까?\n" +
                    "삭제된 레시피는 복구가 불가능합니다.",
            declineText = "취소",
            acceptText = "삭제",
            setShowDialog ={
                showDialogSave.value = it
            },
            onAcceptClick = {
                CoroutineScope(Dispatchers.Main).launch{
                    //Log.e("my-delete-temprecipe2",  "${tempId}")
                    viewModel.deleteTempRecipe(tempId.value)
                    showDialogSave.value = false
                }
            }
        )
    }


}