package com.zipdabang.zipdabang_android.module.main

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import com.zipdabang.zipdabang_android.ui.component.ModalDrawer
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.zipdabang.zipdabang_android.core.navigation.DrawerScreen
import com.zipdabang.zipdabang_android.core.navigation.MainNavGraph
import com.zipdabang.zipdabang_android.core.navigation.MyScreen
import com.zipdabang.zipdabang_android.core.navigation.SPLASH_ROUTE
import com.zipdabang.zipdabang_android.core.navigation.SharedScreen
import com.zipdabang.zipdabang_android.module.bottom.ui.BottomNavigationBar
import com.zipdabang.zipdabang_android.module.main.common.FCMData
import com.zipdabang.zipdabang_android.module.main.common.NotificationTarget
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    outerNavController: NavHostController,
    fcmData: FCMData?
  //  innerNavController: NavHostController
){
    //drawer에 필요한 drawerState랑 scope
  //  val drawerState = rememberDrawerState(DrawerValue.Closed)
  //  val scope = rememberCoroutineScope()



    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val innerNavController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.White,
        contentColor = Color.Black,
        bottomBar = {
            BottomNavigationBar(navController = innerNavController)
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) {
        Box(
            modifier = Modifier.padding(it)
        ) {

            val notificationViewModel = hiltViewModel<NotificationViewModel>()


            // navigate가 호출되는 위치는 NavGraph가 위치한 곳이어야 뒤로가기가 제대로 동작
            LaunchedEffect(key1 = true) {
                Log.d("HomeScreen", "$fcmData")
                fcmData?.let {
                    notificationViewModel.getDeleteNotificationResult(alarmId = it.targetNotificationPK)
                    when (it.targetView) {
                        NotificationTarget.Recipe.target -> {
                            Log.d("HomeScreen", "move to recipe")
                            innerNavController.navigate(
                                route = SharedScreen.DetailRecipe.passRecipeId(it.targetPK)
                            )
                        }
                        NotificationTarget.User.target -> {
                            innerNavController.navigate(
                                route = MyScreen.OtherPage.passUserId(it.targetPK)
                            )
                        }
                        NotificationTarget.MyPage.target -> {
                            innerNavController.navigate(
                                route = DrawerScreen.UserInfo.route
                            )
                        }
                        NotificationTarget.Notification.target -> {

                        }
                    }
                }
            }

            MainNavGraph(
                innerNavController = innerNavController,
                outerNavController = outerNavController,
                showSnackBar = { text ->
                    scope.launch {
                        snackbarHostState.showSnackbar(text)
                    }
                }
            )
        }
    }
}




