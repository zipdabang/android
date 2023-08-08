package com.zipdabang.zipdabang_android.module.detail.recipe

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun DetailRecipeScreen(){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White,
    ){
        Text("Detail_Recipes")
    }
}