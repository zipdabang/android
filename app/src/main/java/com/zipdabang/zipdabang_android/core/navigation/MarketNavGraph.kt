package com.zipdabang.zipdabang_android.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import androidx.paging.ExperimentalPagingApi
import com.zipdabang.zipdabang_android.module.home.ui.GuideScreen
import com.zipdabang.zipdabang_android.module.home.ui.HomeScreen
import com.zipdabang.zipdabang_android.module.market.ui.CategoryMarketScreen
import com.zipdabang.zipdabang_android.module.market.ui.MarketScreen
import com.zipdabang.zipdabang_android.module.market.ui.MarketScreen_Test


@OptIn(ExperimentalPagingApi::class)
fun NavGraphBuilder.MarketNavGraph(navController: NavController){


    navigation(startDestination = MarketScreen.Home.route,route = MARKET_ROUTE){
        composable( MarketScreen.Home.route){
            MarketScreen_Test()
        }
        composable( MarketScreen.Category.route,
            arguments = listOf(navArgument("categoryId"){
                type = NavType.IntType
            })){
            CategoryMarketScreen(categoryId = it.arguments?.getInt("categoryId"))
        }

    }






}