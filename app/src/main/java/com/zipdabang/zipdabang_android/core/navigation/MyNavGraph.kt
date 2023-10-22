package com.zipdabang.zipdabang_android.core.navigation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.zipdabang.zipdabang_android.core.data_store.proto.CurrentPlatform
import com.zipdabang.zipdabang_android.core.data_store.proto.ProtoDataViewModel
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.drawer.ui.NoticeScreen
import com.zipdabang.zipdabang_android.module.my.ui.FriendListScreen
import com.zipdabang.zipdabang_android.module.my.ui.LikeScreen
import com.zipdabang.zipdabang_android.module.my.ui.MyRecipesScreen
import com.zipdabang.zipdabang_android.module.my.ui.MyScreen
import com.zipdabang.zipdabang_android.module.my.ui.MyScreenForNotUser
import com.zipdabang.zipdabang_android.module.my.ui.MyScreenForOther
import com.zipdabang.zipdabang_android.module.my.ui.OtherRecipeListScreen
import com.zipdabang.zipdabang_android.module.my.ui.RecipeWriteScreen
import com.zipdabang.zipdabang_android.module.my.ui.ScrapScreen
import com.zipdabang.zipdabang_android.module.my.ui.ShoppingScreen
import com.zipdabang.zipdabang_android.module.my.ui.viewmodel.RecipeWriteViewModel


@SuppressLint("CoroutineCreationDuringComposition")
fun NavGraphBuilder.MyNavGraph(
    navController: NavHostController,
    outerNavController: NavHostController
) {

    navigation(startDestination = MyScreen.Home.route, route = MY_ROUTE) {
        composable(MyScreen.Home.route) {
            val tokenStoreViewModel = hiltViewModel<ProtoDataViewModel>()

            val currentPlatform = tokenStoreViewModel.tokens.collectAsState(
                initial = Token(
                    null,
                    null,
                    null,
                    CurrentPlatform.NONE,
                    null,
                    null
                )
            )

            if (currentPlatform.value.platformStatus == CurrentPlatform.TEMP) {

                Log.d("myRoute", navController.currentBackStackEntry?.destination?.label.toString())

                MyScreenForNotUser(
                    navController = navController,
                    onClickLogin = {
                        outerNavController.navigate(AUTH_ROUTE){
                            launchSingleTop = true
                        }
                    }
                )
            } else {
                MyScreen(
                    navController = navController,
                    onClickBack = {
                        navController.navigate(MAIN_ROUTE){
                            launchSingleTop = true
                        }
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
                        navController.navigate(MyScreen.FriendList.route)
                    },
                    onClickNotice ={
                        navController.navigate(MyScreen.NoticeList.route)
                    },
                    onClickLogout = {
                        outerNavController.navigate(AUTH_ROUTE){
                            popUpTo(MAIN_ROUTE) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                        Log.e("signup-tokens","로그아웃 클릭, onClick 실행 중")
                    },
                    onClickUserInfo = {
                        navController.navigate(DrawerScreen.UserInfo.route)
                    },
                    onClickAlarm = {

                    },
                    onClickInquiry = {

                    },
                    onClickFriendsList = {
                        navController.navigate(MyScreen.FriendList.route)

                    }
                )
            }
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
            MyRecipesScreen(
                navController = navController,
                onClickBack = {
                    navController.popBackStack(MyScreen.Home.route, inclusive = false)
                },
                onClickWrite = {
                    //navController.navigate(MyScreen.RecipeWrite.passRecipeId(0))
                    navController.navigate(MyScreen.RecipeWrite.passTempId(0))
                },
                onClickCompleteRecipes = {
                    navController.navigate(SharedScreen.DetailRecipe.passRecipeId(it))
                },
                onClickTempRecipes = {
                    navController.navigate(MyScreen.RecipeWrite.passTempId(it))
                    //Log.e("tempId 전달 3","tempId : ${it}")
                },
                onClickCompleteRecipeEdit = {
                    navController.navigate(MyScreen.RecipeEdit.passRecipeId(it))
                    Log.e("recipewrite-get-save","recipeId : ${it}")
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
                },
                onClickOthers = {
                    navController.navigate(MyScreen.OtherPage.passUserId(it))
                }
            )
        }
        composable(
            route = MyScreen.RecipeEdit.route,
            arguments = listOf(navArgument(name = "recipeId") { type = NavType.IntType})
        ){ navBackStackEntry->
            val recipeId = navBackStackEntry.arguments?.getInt("recipeId")

            RecipeWriteScreen(
                tempId = 0,
                recipeId = recipeId,
                onClickBack = {
                    navController.popBackStack()
                },
                onClickViewRecipe = {recipeId ->
                    navController.navigate(
                        route = SharedScreen.DetailRecipe.passRecipeId(recipeId)
                    ) {
                        popUpTo(route = MyScreen.RecipeWrite.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(
            route = MyScreen.RecipeWrite.route,
            arguments = listOf(
                navArgument(name = "tempId") { type = NavType.IntType}
            )
        ) { navBackStackEntry->
            val recipeWriteViewModel = navBackStackEntry
                .recipeWriteViewModel<RecipeWriteViewModel>(navController = navController)
            val tempId = navBackStackEntry.arguments?.getInt("tempId")

            RecipeWriteScreen(
                tempId = tempId,
                recipeId = null,
                onClickBack = {
                    navController.popBackStack(MyScreen.Myrecipe.route, inclusive = false)
                },
                onClickViewRecipe = { recipeId ->
                    navController.navigate(
                        route = SharedScreen.DetailRecipe.passRecipeId(recipeId)
                    ) {
                        popUpTo(route = MyScreen.RecipeWrite.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(MyScreen.NoticeList.route) {
            NoticeScreen(
                navController = navController,
                onClickBack = {
                    navController.popBackStack(MyScreen.Home.route, inclusive = false)
                }
            )
        }

        composable(
            route = MyScreen.OtherPage.route,
            arguments = listOf(
                navArgument("userId"){ type = NavType.IntType },
            )
        ){
            val userId = it.arguments?.getInt("userId")
            if (userId != null) {
                MyScreenForOther(
                    navController = navController,
                    userId = userId,
                    onClickHeader = {
                        nickName ->
                        navController.navigate(MyScreen.OtherRecipeListPage.passUserInfo(userId,nickName))
                    }
                )
            }
        }

        composable(
            route = MyScreen.OtherRecipeListPage.route,
            arguments = listOf(
                navArgument("userId"){ type = NavType.IntType },
                navArgument("nickName"){type = NavType.StringType}
            )
        ){
            val userId = it.arguments!!.getInt("userId")
            val nickName = it.arguments!!.getString("nickName")

            if (nickName != null) {
                OtherRecipeListScreen(
                    nickName = nickName
                )
            }
        }
    }
}


@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.recipeWriteViewModel(navController: NavHostController): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}



