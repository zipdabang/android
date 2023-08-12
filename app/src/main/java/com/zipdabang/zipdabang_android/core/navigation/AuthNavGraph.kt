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
import com.zipdabang.zipdabang_android.common.Constants
import com.zipdabang.zipdabang_android.module.login.ui.LoginScreen
import com.zipdabang.zipdabang_android.module.sign_up.ui.TermsScreen
import dagger.hilt.android.lifecycle.HiltViewModel

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(startDestination = AuthScreen.SignIn.route, route = AUTH_ROUTE) {
        composable(route = AuthScreen.SignIn.route) { navBackStackEntry ->
            val authSharedViewModel = navBackStackEntry
                .authSharedViewModel<AuthSharedViewModel>(navController = navController)
            LoginScreen(
                onSuccess = {
                    navController.navigate(AuthScreen.Terms.route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(route = AuthScreen.Terms.route) { navBackStackEntry ->
            val authSharedViewModel = navBackStackEntry
                .authSharedViewModel<AuthSharedViewModel>(navController = navController)
            TermsScreen(navController = navController, authSharedViewModel = authSharedViewModel)
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