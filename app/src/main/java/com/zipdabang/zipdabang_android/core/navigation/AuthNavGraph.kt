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
                    navController.navigate(AuthScreen.RegisterUserAddress.route)
                }
            )
        }

        composable(route =AuthScreen.RegisterUserAddress.route) {navBackStackEntry ->
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
        }

        composable(route =AuthScreen.RegisterNickname.route) {navBackStackEntry ->
            val authSharedViewModel = navBackStackEntry.authSharedViewModel<AuthSharedViewModel>(navController = navController)
            RegisterNicknameScreen(
                navController = navController,
                authSharedViewModel = authSharedViewModel,
                onClickBack = {
                    navController.popBackStack(AuthScreen.RegisterUserAddress.route, inclusive = false)
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

                },
                onClickNextAfterChoose = {

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