package com.zipdabang.zipdabang_android.module.basket.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun BasketScreen(){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White,
    ){
        Text("basket")
    }
}