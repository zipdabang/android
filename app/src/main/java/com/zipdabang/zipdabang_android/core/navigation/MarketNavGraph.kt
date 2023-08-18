package com.zipdabang.zipdabang_android.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.zipdabang.zipdabang_android.module.home.ui.GuideScreen
import com.zipdabang.zipdabang_android.module.home.ui.HomeScreen
import com.zipdabang.zipdabang_android.module.market.ui.MarketScreen


fun NavGraphBuilder.MarketNavGraph(navController: NavController){


    navigation(startDestination = MarektScreen.Home.route,route = MARKET_ROUTE){
        composable( MarektScreen.Home.route){
            MarketScreen()
        }

    }






}