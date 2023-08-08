package com.zipdabang.zipdabang_android.module.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.zipdabang.zipdabang_android.module.home.HomeGraph

@Composable
fun HomeScreen(
    navController: NavController
){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White,
    ){
        Column{
            Button(
                onClick = {
                    navController.navigate(HomeGraph.DetailRecipe)
                }
            ){
                Text(text = "recipe")
            }

            Button(
                onClick = {
                    navController.navigate(HomeGraph.DetailGoods)
                }
            ){
                Text(text = "goods")
            }

        }
    }
}