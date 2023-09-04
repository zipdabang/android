package com.zipdabang.zipdabang_android.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.zipdabang.zipdabang_android.module.drawer.ui.NoticeScreen
import com.zipdabang.zipdabang_android.module.drawer.ui.UserInfoScreen

fun NavGraphBuilder.DrawerNavGraph(navController: NavController){


    navigation(startDestination = DrawerScreen.Notice.route,route = DRAWER_ROUTE){
        composable( DrawerScreen.Notice.route){
           NoticeScreen(navController)
        }

        composable(DrawerScreen.UserInfo.route){ navBackStackEntry ->
            UserInfoScreen(
                onClickBack = {
                    // 내집다방 탭에 있는 Screen에서 앱바의 뒤로가기를 클릭하면 잘 이동하지만,
                    // 다른 탭에 있는 Screen에서는 앱바의 뒤로가기가 먹히지 않음. 수정 필요.
                    navController.popBackStack(MyScreen.Home.route, inclusive = false)
                },
                onClickEdit = {},
                onClickEditBasic = {},
                onClickEditDetail = {},
                onClickEditNickname = {},
                onClickLogout = {},
                onClickWithdraw = {}
            )
        }
    }






}