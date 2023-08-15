package com.zipdabang.zipdabang_android.module.home.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


@Composable
fun GuideScreen(
){
    LazyColumn(){
       items(500){
           Text("test" + it.toString())
       }
    }
}