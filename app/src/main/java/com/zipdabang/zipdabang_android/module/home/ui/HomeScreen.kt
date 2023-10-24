package com.zipdabang.zipdabang_android.module.home.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.core.navigation.MyScreen
import com.zipdabang.zipdabang_android.core.navigation.SharedScreen
import com.zipdabang.zipdabang_android.module.guide.ui.HomeBanner.GuideBannerSlider
import com.zipdabang.zipdabang_android.module.home.data.bestrecipe.BestRecipe
import com.zipdabang.zipdabang_android.module.item.recipe.ui.RecipeCard
import com.zipdabang.zipdabang_android.module.main.NotificationViewModel
import com.zipdabang.zipdabang_android.module.main.common.FCMData
import com.zipdabang.zipdabang_android.module.main.common.NotificationTarget
import com.zipdabang.zipdabang_android.module.recipes.ui.viewmodel.RecipeMainViewModel
import com.zipdabang.zipdabang_android.ui.component.AppBarHome
import com.zipdabang.zipdabang_android.ui.component.Banner
import com.zipdabang.zipdabang_android.ui.component.GroupHeaderReversedNoIcon
import com.zipdabang.zipdabang_android.ui.component.ModalDrawer
import com.zipdabang.zipdabang_android.ui.shimmeringEffect
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController : NavController,
    onGuide1Click : ()-> Unit,
    onRecipeItemClick : (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
    recipeMainViewModel : RecipeMainViewModel = hiltViewModel()
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
                       onClickEndIcon1 = {navController.navigate(SharedScreen.Search.route)},
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


                            GroupHeaderReversedNoIcon(
                                formerHeaderChoco = "주간 베스트 ",
                                latterHeaderStrawberry = "레시피",
                            )

                           if(recipeState.value.isError){
                                val recipeList = emptyList<BestRecipe>()
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp)
                                ) {

                                }

                                Log.e("HomeScreen Error",bannerState.value.error)
                            }
                            else {


                                LazyRow(
                                    modifier = Modifier.padding(horizontal = 8.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {


                                    if (recipeState.value.isLoading) {

                                        items(10){
                                            Box(modifier = Modifier
                                                .width(160.dp)
                                                .height(228.dp)
                                                .shimmeringEffect()
                                            )

                                        }

                                    } else {
                                        itemsIndexed(recipeState.value.recipeList) { index, item ->

                                            var isLiked by rememberSaveable { mutableStateOf(item.isLiked) }
                                            var isScraped by rememberSaveable { mutableStateOf(item.isScrapped) }
                                            var likes by rememberSaveable { mutableStateOf(item.likes) }
                                            RecipeCard(
                                                recipeId = item.recipeId,
                                                title = item.recipeName,
                                                user = item.nickname,
                                                thumbnail = item.thumbnailUrl,
                                                date = item.createdAt,
                                                likes = likes,
                                                comments = item.comments,
                                                isLikeSelected = isLiked,
                                                isScrapSelected = isScraped,
                                                onLikeClick = {
                                                    recipeMainViewModel.toggleLike(item.recipeId)
                                                    isLiked = !isLiked
                                                    item.isLiked = !item.isLiked
                                                    if (isLiked) {
                                                        item.likes += 1
                                                    } else {
                                                        item.likes -= 1
                                                    }
                                                    likes = item.likes

                                                    // 예은 임시로 해둠
                                                },
                                                onScrapClick = {
                                                    recipeMainViewModel.toggleScrap(item.recipeId)
                                                    isScraped = !isScraped
                                                    //   item.isScrapped=  !item.isScrapped

                                                    // 예은 임시로 해둠
                                                },
                                                onItemClick = {
                                                    onRecipeItemClick(item.recipeId)
                                                }
                                            )
                                        }
                                    }

                                }
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            //가이드 배너
                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .padding(horizontal = 20.dp)){
                                GuideBannerSlider(onGuide1Click)
                            }

                        }

                }
            )
        },
        drawerState = drawerState,
        navController = navController
    )

}