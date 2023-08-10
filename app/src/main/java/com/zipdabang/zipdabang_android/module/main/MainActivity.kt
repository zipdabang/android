package com.zipdabang.zipdabang_android.module.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZipdabangandroidTheme {
                ZipdabangApp()

            }
        }
    }
}