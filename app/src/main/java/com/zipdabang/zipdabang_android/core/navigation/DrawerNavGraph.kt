package com.zipdabang.zipdabang_android.core.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navigation
import com.zipdabang.zipdabang_android.module.drawer.ui.NoticeScreen
import com.zipdabang.zipdabang_android.module.drawer.ui.UserInfoBasicScreen
import com.zipdabang.zipdabang_android.module.drawer.ui.UserInfoDetailScreen
import com.zipdabang.zipdabang_android.module.drawer.ui.UserInfoNicknameScreen
import com.zipdabang.zipdabang_android.module.drawer.ui.UserInfoScreen
import com.zipdabang.zipdabang_android.module.drawer.ui.viewmodel.DrawerUserInfoViewModel

fun NavGraphBuilder.DrawerNavGraph(navController: NavHostController){

    navigation(startDestination = DrawerScreen.Notice.route, route = DRAWER_ROUTE){
        composable(DrawerScreen.Notice.route){
           NoticeScreen(navController)
        }

        composable(DrawerScreen.UserInfo.route){ navBackStackEntry ->
            val drawerUserInfoViewModel = navBackStackEntry
                .drawerUserInfoViewModel<DrawerUserInfoViewModel>(navController = navController)

            UserInfoScreen(
                drawerUserInfoViewModel = drawerUserInfoViewModel,
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

        composable(DrawerScreen.UserInfoBasic.route){ navBackStackEntry ->
            val drawerUserInfoViewModel = navBackStackEntry
                .drawerUserInfoViewModel<DrawerUserInfoViewModel>(navController = navController)

            UserInfoBasicScreen(
                drawerUserInfoViewModel = drawerUserInfoViewModel,
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

        composable(DrawerScreen.UserInfoDetail.route){navBackStackEntry ->
            val drawerUserInfoViewModel = navBackStackEntry
                .drawerUserInfoViewModel<DrawerUserInfoViewModel>(navController = navController)

            UserInfoDetailScreen(
                drawerUserInfoViewModel = drawerUserInfoViewModel,
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

        composable(DrawerScreen.UserInfoNickname.route){ navBackStackEntry ->
            val drawerUserInfoViewModel = navBackStackEntry
                .drawerUserInfoViewModel<DrawerUserInfoViewModel>(navController = navController)

            UserInfoNicknameScreen(
                drawerUserInfoViewModel = drawerUserInfoViewModel,
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

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.drawerUserInfoViewModel(navController: NavHostController): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}