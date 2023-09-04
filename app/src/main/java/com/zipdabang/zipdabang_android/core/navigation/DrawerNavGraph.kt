package com.zipdabang.zipdabang_android.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navigation
import com.zipdabang.zipdabang_android.module.drawer.ui.NoticeScreen
import com.zipdabang.zipdabang_android.module.drawer.ui.UserInfoBasicScreen
import com.zipdabang.zipdabang_android.module.drawer.ui.UserInfoDetailScreen
import com.zipdabang.zipdabang_android.module.drawer.ui.UserInfoNicknameScreen
import com.zipdabang.zipdabang_android.module.drawer.ui.UserInfoScreen

fun NavGraphBuilder.DrawerNavGraph(navController: NavController){

    navigation(startDestination = DrawerScreen.Notice.route,route = DRAWER_ROUTE){
        composable(DrawerScreen.Notice.route){
           NoticeScreen(navController)
        }

        composable(DrawerScreen.UserInfo.route){ navBackStackEntry ->
            UserInfoScreen(
                onClickBack = {
                    navController.navigateUp()
                },
                onClickEdit = {},
                onClickEditBasic = {
                    navController.navigate(DrawerScreen.UserInfoBasic.route)
                },
                onClickEditDetail = {
                    navController.navigate(DrawerScreen.UserInfoDetail.route)
                },
                onClickEditNickname = {
                    navController.navigate(DrawerScreen.UserInfoNickname.route)
                },
                onClickLogout = {},
                onClickWithdraw = {}
            )
        }

        composable(DrawerScreen.UserInfoBasic.route){
            UserInfoBasicScreen(
                onClickBack = {
                    navController.popBackStack(DrawerScreen.UserInfo.route, inclusive = false)
                },
                onClickCancel = {
                    navController.popBackStack(DrawerScreen.UserInfo.route, inclusive = false)
                },
                onClickEdit = {
                    navController.popBackStack(DrawerScreen.UserInfo.route, inclusive = false)
                }
            )
        }

        composable(DrawerScreen.UserInfoDetail.route){
            UserInfoDetailScreen(
                onClickBack = {
                    navController.popBackStack(DrawerScreen.UserInfo.route, inclusive = false)
                },
                onClickCancel = {
                    navController.popBackStack(DrawerScreen.UserInfo.route, inclusive = false)
                },
                onClickEdit = {
                    navController.popBackStack(DrawerScreen.UserInfo.route, inclusive = false)
                }
            )
        }

        composable(DrawerScreen.UserInfoNickname.route){
            UserInfoNicknameScreen(
                onClickBack = {
                    navController.popBackStack(DrawerScreen.UserInfo.route, inclusive = false)
                },
                onClickCancel = {
                    navController.popBackStack(DrawerScreen.UserInfo.route, inclusive = false)
                },
                onClickEdit = {
                    navController.popBackStack(DrawerScreen.UserInfo.route, inclusive = false)
                }
            )
        }
    }






}