package com.zipdabang.zipdabang_android.module.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.zipdabang.zipdabang_android.module.login.ui.LoginScreen
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZipdabangandroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(ZipdabangandroidTheme.Shapes.small),
                    color = ZipdabangandroidTheme.Colors.Strawberry,
                ) {
                    /*Column(){
                        Greeting("집다방","홈카페를 위한 모든 것이 여기에!")
                    }*/
                    LoginScreen()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, name2: String, modifier: Modifier = Modifier) {
    Column(){
        Text(
            text = "$name",
            modifier = Modifier.background(ZipdabangandroidTheme.Colors.Cream),
            style = ZipdabangandroidTheme.Typography.logo,
            color = ZipdabangandroidTheme.Colors.Choco,)
        Text(
            text = "$name2",
            modifier = Modifier.background(ZipdabangandroidTheme.Colors.Cream),
            style = ZipdabangandroidTheme.Typography.underLogo,
            color = ZipdabangandroidTheme.Colors.Choco,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ZipdabangandroidTheme {
        Greeting("집다방","홈카페를 위한 모든 것이 여기에!")
    }
}