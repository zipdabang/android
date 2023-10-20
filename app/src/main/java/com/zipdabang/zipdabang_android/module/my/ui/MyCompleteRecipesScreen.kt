package com.zipdabang.zipdabang_android.module.my.ui

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.zipdabang.zipdabang_android.module.my.ui.component.myrecipes.CompleteRecipeItem
import com.zipdabang.zipdabang_android.module.my.ui.viewmodel.MyRecipesViewModel
import com.zipdabang.zipdabang_android.ui.component.CustomDialogType1
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun MyCompleteRecipesScreen(
    onClickCompleteRecipes : (Int)->Unit,
    viewModel : MyRecipesViewModel = hiltViewModel()
) {
    val completeRecipeItems = viewModel.completeRecipeItems.collectAsLazyPagingItems()
    val showDialogSave = remember { mutableStateOf(false) }
    var recipeId = remember { mutableStateOf(0) }

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
                },
                onDeleteClick = {
                    recipeId.value = completeRecipeItems[it]!!.recipeId
                    showDialogSave.value = true
                },
                onEditClick = {
                    // 수정하는 페이지로 페이지 이동
                }
            )
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
                    viewModel.deleteCompleteRecipe(recipeId.value)
                    showDialogSave.value = false
                }
            }
        )
    }

}