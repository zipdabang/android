package com.zipdabang.zipdabang_android.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.zipdabang.zipdabang_android.module.basket.ui.BasketScreen
import com.zipdabang.zipdabang_android.module.drawer.ui.NoticeScreen

fun NavGraphBuilder.DrawerNavGraph(navController: NavController){


    navigation(startDestination = DrawerScreen.Notice.route,route = DRAWER_ROUTE){
        composable( DrawerScreen.Notice.route){
           NoticeScreen(navController)
        }

    }






}