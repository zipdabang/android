package com.zipdabang.zipdabang_android.core.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.zipdabang.zipdabang_android.module.main.common.FCMData

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainNavGraph(
    innerNavController: NavHostController,
    outerNavController: NavHostController,
    showSnackBar: (String) -> Unit,
    fcmData: FCMData?,
    onFcmDataExist: () -> Unit
){
    NavHost(navController = innerNavController, startDestination = HOME_ROUTE , route = MAIN_ROUTE)
    {


        Log.d("auth graph", "home")
        HomeNavGraph(navController = innerNavController, fcmData = fcmData, onFcmDataExist = onFcmDataExist)

        MarketNavGraph(navController = innerNavController)

        BasketNavGraph(navController = innerNavController)

        RecipeNavGraph(
            navController = innerNavController,
            outerNavController = outerNavController,
            showSnackBar = showSnackBar
        )

        MyNavGraph(
            navController = innerNavController,
            outerNavController = outerNavController
        )

        SharedNavGraph(
            navController = innerNavController,
            outerNavController = outerNavController,
            showSnackBar = showSnackBar
        )

        DrawerNavGraph(navController = innerNavController,
            outerNavController= outerNavController)


    }

}





