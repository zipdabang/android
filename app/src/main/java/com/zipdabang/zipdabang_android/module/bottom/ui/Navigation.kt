package com.zipdabang.zipdabang_android.module.bottom.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun Navigation(
    navController: NavHostController
){
    NavHost(navController =  navController, startDestination = "home"){

        composable("market"){
            MarketScreen()
        }
        composable("basket"){
            BasketScreen()
        }
        composable("home"){
            HomeScreen()
        }
        composable("recipes"){
            RecipesScreen()
        }
        composable("my"){
            MyScreen()
        }

    }
}

@Composable
fun MarketScreen(){
    Text("market")
}
@Composable
fun HomeScreen(){
    Text("home")
}

@Composable
fun BasketScreen(){
    Text("Basket")
}

@Composable
fun RecipesScreen(){
    Text("recipes")
}@Composable
fun MyScreen(){
    Text("my")
}