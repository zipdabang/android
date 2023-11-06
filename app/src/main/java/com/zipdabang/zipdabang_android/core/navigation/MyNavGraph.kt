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
import com.zipdabang.zipdabang_android.module.my.ui.MyRecipeListScreen
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
    outerNavController: NavHostController,
    showSnackBar: (String) -> Unit
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
                        navController.navigate(MyScreen.Shopping.route)
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
                        navController.navigate(DrawerScreen.ReportList.route)
                    },
                    onClickFriendsList = {
                        navController.navigate(MyScreen.FriendList.route)

                    },
                    onClickMyRecipeList = {
                        navController.navigate(MyScreen.MyRecipeList.passNickname(it))
                    },
                    onRecipeItemClick = {recipeId->
                        navController.navigate(SharedScreen.DetailRecipe.passRecipeId(recipeId))
                    },
                    showSnackBar = showSnackBar
                )
            }
        }

        composable(MyScreen.Like.route) {
            LikeScreen(
                onClickBack = {
                    navController.popBackStack(MyScreen.Home.route, inclusive = false)
                },
                onRecipeItemClick = { recipeId->
                    navController.navigate(SharedScreen.DetailRecipe.passRecipeId(recipeId))
                }
            )
        }

        composable(MyScreen.Scrap.route) {
            ScrapScreen(
                onClickBack = {
                    navController.popBackStack(MyScreen.Home.route, inclusive = false)
                },
                onRecipeItemClick = { recipeId->
                    navController.navigate(SharedScreen.DetailRecipe.passRecipeId(recipeId))
                }
            )
        }

        composable(
            route = MyScreen.MyRecipeList.route,
            arguments = listOf(navArgument(name = "nickname") { type = NavType.StringType})
        ){ navBackStackEntry->
            val nickname = navBackStackEntry.arguments?.getString("nickname")

            MyRecipeListScreen(
                nickname = nickname!!,
                onClickBack = {
                    navController.popBackStack(MyScreen.Home.route, inclusive = false)
                },
                onRecipeItemClick = { recipeId->
                    navController.navigate(SharedScreen.DetailRecipe.passRecipeId(recipeId))
                },
                onClickMyrecipe = {
                    navController.navigate(MyScreen.RecipeWrite.passTempId(0))
                },
                showSnackBar= showSnackBar
            )
        }

        composable(MyScreen.Myrecipe.route) {
            MyRecipesScreen(
                navController = navController,
                onClickBack = {
                    navController.popBackStack()
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

        // 레시피 수정할때 레시피 수정 페이지
        composable(
            route = MyScreen.RecipeEdit.route,
            arguments = listOf(navArgument(name = "recipeId") { type = NavType.IntType})
        ){ navBackStackEntry->
            val recipeId = navBackStackEntry.arguments?.getInt("recipeId")

            RecipeWriteScreen(
                navController = navController,
                tempId = 0,
                recipeId = recipeId,
                onClickBack = {
                    navController.navigateUp()
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
                },
                onClickNextTimeInEdit = {
                    navController.popBackStack()
                    navController.popBackStack()
                }
            )
        }

        // 처음 업로드나 임시저장 수정할때 페이지
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
                navController = navController,
                tempId = tempId,
                recipeId = null,
                onClickBack = {
                    navController.navigateUp()
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
                },
                onClickNextTimeInEdit = { }
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
                    },
                    onRecipeItemClick = {id ->
                        navController.navigate(SharedScreen.DetailRecipe.passRecipeId(id))
                    },
                    onClickBack = {
                        navController.navigateUp()
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
                    nickName = nickName,
                    onRecipeItemClick =  {
                        navController.navigate(SharedScreen.DetailRecipe.passRecipeId(it))
                    },
                    onClickBack = {
                        navController.navigateUp()
                    }
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



