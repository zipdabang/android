package com.zipdabang.zipdabang_android.module.home.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.home.data.bestrecipe.BestRecipeDto
import com.zipdabang.zipdabang_android.module.home.data.bestrecipe.Recipe
import com.zipdabang.zipdabang_android.module.item.recipe.ui.RecipeCard
import com.zipdabang.zipdabang_android.ui.component.AppBarHome
import com.zipdabang.zipdabang_android.ui.component.Banner
import com.zipdabang.zipdabang_android.ui.component.GroupHeader
import com.zipdabang.zipdabang_android.ui.component.GroupHeaderReversed
import com.zipdabang.zipdabang_android.ui.component.ModalDrawer
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController : NavController,
    viewModel: HomeViewModel = hiltViewModel()
){
    //drawer에 필요한 drawerState랑 scope
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val bannerState = viewModel.bannerState
    val recipeState = viewModel.recipeState

    ModalDrawer(
        scaffold = {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                   AppBarHome(
                        endIcon1 = R.drawable.ic_topbar_search,
                        endIcon2 = R.drawable.ic_topbar_menu,
                       onClickEndIcon1 = {},
                        onClickEndIcon2 = { scope.launch { drawerState.open() } },
                        centerText = "집다방"
                    )
                },
                containerColor = Color.White,
                contentColor = Color.Black,
                    content = {

                        val scrollState = rememberScrollState()
                        Column(
                       modifier = Modifier
                           .padding(top = it.calculateTopPadding())
                           .verticalScroll(scrollState)
                   ) {

                            if (bannerState.value.isLoading) {
                           //Shimmering Effect
                            }
                            else if(bannerState.value.isError){
                                val imageUrlList = emptyList<String>()
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp)
                                ) {
                                    Banner(imageUrlList)
                                }
                                Log.e("HomeScreen Error",bannerState.value.error)
                            }
                            else {
                                val bannerListState = bannerState.value.bannerList
                                bannerListState.let {
                                    val imageUrlList: List<String> =
                                        bannerListState.map { it.imageUrl }
                                    Banner(imageUrlList)
                                }
                            }


                            GroupHeaderReversed(
                                groupName = "Home_Title",
                                formerHeaderChoco = "주간 베스트 ",
                                latterHeaderStrawberry = "레시피",
                                onClick = { }
                            )

                            if (recipeState.value.isLoading) {
                                //Shimmering Effect
                            }
                            else if(recipeState.value.isError){
                                val recipeList = emptyList<Recipe>()
                                LazyRow(modifier = Modifier.padding(horizontal = 4.dp)) {
                                    itemsIndexed(recipeList) { index, item ->
                                        RecipeCard(
                                            recipeId = item.recipeId,
                                            title = item.recipeName,
                                            user = item.owner,
                                            thumbnail = item.thumbnailUrl,
                                            date = item.createdAt,
                                            likes = item.likes,
                                            comments = item.comments,
                                            isLikeSelected = item.isLiked,
                                            isScrapSelected = item.isScrapped,
                                            onLikeClick = {
                                                 true // 예은 임시로 해둠
                                            },
                                            onScrapClick = {
                                                 true // 예은 임시로 해둠
                                            },
                                            onItemClick = {}
                                        )
                                    }
                                }



                                Log.e("HomeScreen Error",bannerState.value.error)
                            }
                            else {
                                LazyRow(modifier = Modifier.padding(horizontal = 4.dp)) {
                                    itemsIndexed(recipeState.value.recipeList) { index, item ->
                                        RecipeCard(
                                            recipeId = item.recipeId,
                                            title = item.recipeName,
                                            user = item.owner,
                                            thumbnail = item.thumbnailUrl,
                                            date = item.createdAt,
                                            likes = item.likes,
                                            comments = item.comments,
                                            isLikeSelected = item.isLiked,
                                            isScrapSelected = item.isScrapped,
                                            onLikeClick = {
                                                true // 예은 임시로 해둠
                                            },
                                            onScrapClick = {
                                                true // 예은 임시로 해둠
                                            },
                                            onItemClick = {}
                                        )
                                    }
                                }


                            }


                        }

                }
            )
        },
        drawerState = drawerState,
        navController = navController
    )

}