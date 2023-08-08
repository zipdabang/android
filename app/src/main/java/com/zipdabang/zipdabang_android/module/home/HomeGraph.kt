package com.zipdabang.zipdabang_android.module.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.internal.composableLambda
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navigation
import com.zipdabang.zipdabang_android.module.detail.goods.DetailGoodsScreen
import com.zipdabang.zipdabang_android.module.detail.recipe.DetailRecipeScreen
import com.zipdabang.zipdabang_android.module.home.ui.HomeScreen

fun NavGraphBuilder.HomeGraph(navController: NavHostController){

    navigation(startDestination = HomeGraph.Home, route = "home_graph") {
            composable(HomeGraph.Home) {
                HomeScreen(navController)
            }
            composable(HomeGraph.DetailRecipe) {
                DetailRecipeScreen()
            }
            composable(HomeGraph.DetailGoods) {
                DetailGoodsScreen()
            }

        }

}



object HomeGraph{
    const val Home = "home"
    const val DetailRecipe = "detail_recipe"
    const val DetailGoods = "detail_goods"
}