package com.zipdabang.zipdabang_android.core.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.zipdabang.zipdabang_android.core.data_store.proto.ProtoDataViewModel
import com.zipdabang.zipdabang_android.module.login.ui.LoginScreen
import com.zipdabang.zipdabang_android.module.sign_up.ui.viewmodel.AuthSharedViewModel
import com.zipdabang.zipdabang_android.module.sign_up.ui.RegisterNicknameScreen
import com.zipdabang.zipdabang_android.module.sign_up.ui.RegisterPreferencesScreen
import com.zipdabang.zipdabang_android.module.sign_up.ui.RegisterUserAddressScreen
import com.zipdabang.zipdabang_android.module.sign_up.ui.RegisterUserInfoScreen
import com.zipdabang.zipdabang_android.module.sign_up.ui.TermDetailScreen
import com.zipdabang.zipdabang_android.module.sign_up.ui.TermsScreen

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(startDestination = AuthScreen.SignIn.route, route = AUTH_ROUTE) {
        composable(route = AuthScreen.SignIn.route) { navBackStackEntry ->
            val authSharedViewModel = navBackStackEntry
                .authSharedViewModel<AuthSharedViewModel>(navController = navController)
            LoginScreen(
                onSuccess = { email, profile ->
                    authSharedViewModel.apply {
                        updateEmail(email)
                        updateProfile(profile)
                    }
                    navController.navigate(MAIN_ROUTE) {
                        popUpTo(AuthScreen.SignIn.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },

                onRegister = { email, profile ->
                    authSharedViewModel.apply {
                        updateEmail(email)
                        updateProfile(profile)
                    }
                    navController.navigate(AuthScreen.Terms.route) {
                        launchSingleTop = true
                    }
                },

                onLoginLater = {
                    navController.navigate(MAIN_ROUTE){
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(route = AuthScreen.Terms.route) { navBackStackEntry ->
            val authSharedViewModel = navBackStackEntry.authSharedViewModel<AuthSharedViewModel>(navController = navController)
            TermsScreen(
                navController = navController,
                authSharedViewModel = authSharedViewModel,
                onClickBack = {
                    navController.popBackStack(AuthScreen.SignIn.route, inclusive = false)
                },
                onClickNext = {
                    navController.navigate(AuthScreen.RegisterUserInfo.route)
                },
                onClickDetailNext = { termIndex ->
                    navController.navigate(AuthScreen.TermDetail.route + "/$termIndex")
                }
            )
        }

        composable(route = AuthScreen.TermDetail.route + "/{termIndex}") { navBackStackEntry ->
            val termIndex = navBackStackEntry.arguments?.getString("termIndex")?.toIntOrNull() ?: 0
            val authSharedViewModel = navBackStackEntry.authSharedViewModel<AuthSharedViewModel>(navController = navController)
            TermDetailScreen(
                navController = navController,
                authSharedViewModel = authSharedViewModel,
                onClickBack = {
                    navController.popBackStack(AuthScreen.Terms.route, inclusive = false)
                },
                termIndex = termIndex
            )
        }

        composable(route =AuthScreen.RegisterUserInfo.route) {navBackStackEntry ->
            val authSharedViewModel = navBackStackEntry.authSharedViewModel<AuthSharedViewModel>(navController = navController)
            RegisterUserInfoScreen(
                navController = navController,
                authSharedViewModel = authSharedViewModel,
                onClickBack = {
                    navController.popBackStack(AuthScreen.Terms.route, inclusive = false)
                },
                onClickNext = {
                    navController.navigate(AuthScreen.RegisterNickname.route)
                }
            )
        }

        // 상세 주소
        /*composable(route =AuthScreen.RegisterUserAddress.route) {navBackStackEntry ->
            val authSharedViewModel = navBackStackEntry.authSharedViewModel<AuthSharedViewModel>(navController = navController)
            RegisterUserAddressScreen(
                navController = navController,
                authSharedViewModel = authSharedViewModel,
                onClickBack = {
                    navController.popBackStack(AuthScreen.RegisterUserInfo.route, inclusive = false)
                },
                onClickNext = {
                    navController.navigate(AuthScreen.RegisterNickname.route)
                }
            )
        }*/

        composable(route =AuthScreen.RegisterNickname.route) {navBackStackEntry ->
            val authSharedViewModel = navBackStackEntry.authSharedViewModel<AuthSharedViewModel>(navController = navController)
            RegisterNicknameScreen(
                navController = navController,
                authSharedViewModel = authSharedViewModel,
                onClickBack = {
                    navController.popBackStack(AuthScreen.RegisterUserInfo.route, inclusive = false)
                },
                onClickNext = {
                    navController.navigate(AuthScreen.RegisterPreferences.route)
                }
            )
        }

        composable(route =AuthScreen.RegisterPreferences.route) {navBackStackEntry ->
            val authSharedViewModel = navBackStackEntry.authSharedViewModel<AuthSharedViewModel>(navController = navController)
            //val tokenStoreViewModel = hiltViewModel<ProtoDataViewModel>()

            RegisterPreferencesScreen(
                navController = navController,
                authSharedViewModel = authSharedViewModel,
                onClickBack = {
                    navController.popBackStack(AuthScreen.RegisterNickname.route, inclusive = false)
                },
                onClickNext = {
                    Log.e("signup-tokens", "넘어가져2")
                    // 내일 myinfo에 accessToken 넣었는데 안된다, MyScreen을 위한 api를 따로 만들어주실건지, 회원정보 삭제 부탁

                    // home에서 뒤로가기했을때 signup 화면이 나오면 안되는데, 지금 이게 안됨 -> 수정했는데 실험해봐야함
                    // drawer가 열려있는 상태일때 뒤로가기를 하면 닫히도록 바꿔야함
                    // my ui 나온거 전부 디자인하기
                    // response enum class로 옮기기
                    navController.navigate(MAIN_ROUTE){
                        popUpTo(AuthScreen.SignIn.route) {
                            inclusive = false //이 경로를 포함하지 않고 제거
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.authSharedViewModel(navController: NavHostController): T {
    // authnavgraph의 상위 NavGraph(parent, 여기서는 RootNavGraph) route를 얻기 위함
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}