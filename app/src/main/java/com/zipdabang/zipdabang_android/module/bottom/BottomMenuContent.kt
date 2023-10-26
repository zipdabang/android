package com.zipdabang.zipdabang_android.module.bottom

import com.zipdabang.zipdabang_android.R



sealed class
BottomMenuContent(
    val title: String,
    val route: String,
    val inactiveIcon: Int,
    val activeIcon: Int
){
    object market : BottomMenuContent(
        "마켓",
        "market/home",
        R.drawable.nav_market_inactive,
        R.drawable.nav_market_active
    )

    object basket : BottomMenuContent(
        "장바구니",
        "basket/home",
        R.drawable.nav_basket_inactive,
        R.drawable.nav_basket_actvie
    )

    object home : BottomMenuContent(
        "홈",
        "home/home",
        R.drawable.nav_home_inactive,
        R.drawable.nav_home_active
    )

    object recipes : BottomMenuContent(
        "레시피",
        "recipes/home",
        R.drawable.nav_recipe_inactive,
        R.drawable.nav_recipe_actvie
    )
    object my : BottomMenuContent(
        "내집다방",
        "my/home",
        R.drawable.nav_my_inactive,
        R.drawable.nav_my_active
    )

}