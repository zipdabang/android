package com.zipdabang.zipdabang_android.module.my.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.item.recipe.ui.RecipeCard
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.follow.search.FollowInfoDB
import com.zipdabang.zipdabang_android.module.my.data.remote.otherinfo.OtherRecipe
import com.zipdabang.zipdabang_android.module.my.ui.viewmodel.OtherRecipeListViewModel
import com.zipdabang.zipdabang_android.ui.component.AppBarDefault
import com.zipdabang.zipdabang_android.ui.component.FriendListSearchBar
import com.zipdabang.zipdabang_android.ui.component.ModalDrawer
import com.zipdabang.zipdabang_android.ui.component.SearchBar
import com.zipdabang.zipdabang_android.ui.shimmeringEffect
import kotlinx.coroutines.launch


@Composable
fun OtherRecipeListScreen(
    nickName : String,
    onClickBack : () -> Unit={},
    otherRecipeListViewModel: OtherRecipeListViewModel = hiltViewModel()
){

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var isLoading  by remember {
        mutableStateOf(false)
    }

    val recipeListItem : LazyPagingItems<OtherRecipe> = otherRecipeListViewModel.recipeList.collectAsLazyPagingItems()
    when(recipeListItem.loadState.refresh)
    {
        is LoadState.Loading -> {
            isLoading = true
            Log.e("isLoading","true")
        }
        else -> {
            isLoading = false
            Log.e("isLoading","false")

        }
    }
    ModalDrawer(
        scaffold = {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize(),
                topBar = {
                    AppBarDefault(
                        startIcon = R.drawable.ic_topbar_backbtn,
                        endIcon = R.drawable.ic_topbar_menu,
                        onClickStartIcon = { onClickBack() },
                        onClickEndIcon = { scope.launch { drawerState.open() } },
                        centerText = "${nickName}의 레시피"
                    )
                },
                containerColor = Color.White,
                contentColor = Color.White,
            ) {

                Column(
                    modifier = Modifier
                        .padding(top = it.calculateTopPadding(), start = 16.dp, end = 16.dp)
                        .fillMaxSize()
                        .background(Color.White)
                ) {

                    SearchBar(hintText = "찾는 것이 있으신가요?")

                    Spacer(modifier = Modifier.height(10.dp))

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                        ){
                        if(!isLoading) {
                            items(recipeListItem.itemCount) {

                                RecipeCard(
                                    recipeId = recipeListItem[it]!!.recipeId,
                                    title = recipeListItem[it]!!.recipeName,
                                    user = recipeListItem[it]!!.nickname,
                                    thumbnail = recipeListItem[it]!!.thumbnailUrl,
                                    date = recipeListItem[it]!!.createdAt,
                                    likes = recipeListItem[it]!!.likes,
                                    comments = recipeListItem[it]!!.comments,
                                    isLikeSelected = recipeListItem[it]!!.isLiked,
                                    isScrapSelected = recipeListItem[it]!!.isScrapped,
                                    onLikeClick = {},
                                    onScrapClick = {},
                                    onItemClick = {}
                                )
                            }
                        }else{

                           items(10){
                               Box(modifier = Modifier
                               .width(160.dp)
                               .height(228.dp)
                               .shimmeringEffect()
                           )



                         }

                     }
                     }




                     }

                    }
            }  ,
        drawerState = drawerState
    )

}



