package com.zipdabang.zipdabang_android.module.bottom

import com.zipdabang.zipdabang_android.R



sealed class BottomMenuContent(
    val title: String,
    val route: String,
    val inactiveIcon: Int,
    val activeIcon: Int
){
    object market : BottomMenuContent(
        "마켓",
        "market/home",
        R.drawable.ic_nav_inactive_market,
        R.drawable.ic_nav_active_market
    )

    object basket : BottomMenuContent(
        "장바구니",
        "basket/home",
        R.drawable.ic_nav_inactive_basket,
        R.drawable.ic_nav_active_basket
    )

    object home : BottomMenuContent(
        "홈",
        "home/home",
        R.drawable.ic_nav_inactive_home,
        R.drawable.ic_nav_active_home
    )

    object recipes : BottomMenuContent(
        "레시피",
        "recipes/home",
        R.drawable.ic_nav_inactive_recipes,
        R.drawable.ic_nav_active_recipes
    )
    object my : BottomMenuContent(
        "내집다방",
        "my/home",
        R.drawable.ic_nav_inactive_my,
        R.drawable.ic_nav_active_my
    )

}