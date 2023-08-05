package com.zipdabang.zipdabang_android.module.my.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun MyScreen(){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White,
    ){
        Text("my")
    }
}