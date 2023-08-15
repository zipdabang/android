package com.zipdabang.zipdabang_android.module.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.zipdabang.zipdabang_android.core.data_store.test.DataStoreTestScreen
import com.zipdabang.zipdabang_android.core.navigation.RootNavGraph
import com.zipdabang.zipdabang_android.module.login.ui.LoginScreen
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZipdabangandroidTheme {
                /*ZipdabangApp()*/
                val navController = rememberNavController()
                RootNavGraph(navController = navController)

            }
        }
    }
}