package com.zipdabang.zipdabang_android.core.navigation

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.zipdabang.zipdabang_android.module.my.ui.FriendListScreen
import com.zipdabang.zipdabang_android.module.my.ui.LikeScreen
import com.zipdabang.zipdabang_android.module.my.ui.MyScreen
import com.zipdabang.zipdabang_android.module.my.ui.MyrecipeScreen
import com.zipdabang.zipdabang_android.module.my.ui.RecipeWriteScreen
import com.zipdabang.zipdabang_android.module.my.ui.ScrapScreen
import com.zipdabang.zipdabang_android.module.my.ui.ShoppingScreen


fun NavGraphBuilder.MyNavGraph(navController: NavHostController) {
    // drawer가 열려있는 상태일때 뒤로가기를 하면 닫히도록 바꿔야함 -> 이거 해야됨

    // 회원정보 불러오기랑 수정하기 api 되면 작업하기 -> 남여 표시 해결 그리고 수정 페이지 갈때마다 api 호출
    // 레시피 작성 ui랑 api 개발하기

    // api 연결한거 shimmering effet 쓰기
    // response enum class로 옮기기
    navigation(startDestination = MyScreen.Home.route, route = MY_ROUTE) {
        composable(MyScreen.Home.route) {
            MyScreen(
                navController = navController,
                onClickBack = {
                    navController.navigate(MAIN_ROUTE){
                        launchSingleTop = true
                    }
                },
                onClickEdit = {

                },
                onClickLike = {
                    navController.navigate(MyScreen.Like.route)
                },
                onClickScrap = {
                    navController.navigate(MyScreen.Scrap.route)
                },
                onClickMyrecipe = {
                    navController.navigate(MyScreen.Myrecipe.route)
                },
                onClickShopping = {
                    navController.navigate(MyScreen.Shopping.route)
                },
                onClickFriendList = {
                    navController.navigate(MyScreen.FriendList.route)
                },
                onClickLogout = {
                    navController.navigate(MAIN_ROUTE){
                        launchSingleTop = true
                    }
                    Log.e("signup-tokens","로그아웃 클릭, onClick 실행 중")
                },
                onClickUserInfo = {
                    navController.navigate(DrawerScreen.UserInfo.route)
                }
            )
        }

        composable(MyScreen.Like.route) {
            LikeScreen(
                onClickBack = {
                    navController.popBackStack(MyScreen.Home.route, inclusive = false)
                }
            )
        }
        composable(MyScreen.Scrap.route) {
            ScrapScreen(
                onClickBack = {
                    navController.popBackStack(MyScreen.Home.route, inclusive = false)
                }
            )
        }
        composable(MyScreen.Myrecipe.route) {
            MyrecipeScreen(
                navController = navController,
                onClickBack = {
                    navController.popBackStack(MyScreen.Home.route, inclusive = false)
                },
                onClickWrite = {
                    navController.navigate(MyScreen.RecipeWrite.route)
                }
            )
        }
        composable(MyScreen.Shopping.route) {
            ShoppingScreen(
                navController = navController,
                onClickBack = {
                    navController.popBackStack(MyScreen.Home.route, inclusive = false)
                }
            )
        }
        composable(MyScreen.FriendList.route) {
            FriendListScreen(
                navController =navController,
                onClickBack = {
                    navController.popBackStack(MyScreen.Home.route, inclusive = false)
                }
            )
        }
        composable(MyScreen.RecipeWrite.route){
            RecipeWriteScreen(
                onClickBack = {
                    navController.popBackStack(MyScreen.Myrecipe.route, inclusive = false)
                }
            )
        }
    }
}




