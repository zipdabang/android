package com.zipdabang.zipdabang_android.core.navigation

const val ROOT_ROUTE = "root"
const val SPLASH_ROUTE = "splash"
const val AUTH_ROUTE = "auth"
const val MAIN_ROUTE = "main"
const val HOME_ROUTE = "main/home"
const val MARKET_ROUTE = "main/market"
const val BASKET_ROUTE = "main/basket"
const val RECIPES_ROUTE = "main/recipes"
const val MY_ROUTE = "main/my"
const val DRAWER_ROUTE = "main/drawer"
const val SHARED_ROUTE = "main/shared"

sealed class AuthScreen(val route: String) {
    object SignIn: AuthScreen(route = "auth/sign_in")
    object Terms: AuthScreen(route = "auth/sign_up/terms")
    object TermDetail : AuthScreen(route = "auth/sign_up/term_detail")
    object RegisterUserInfo: AuthScreen(route = "auth/sign_up/user_info")
    object RegisterUserAddress: AuthScreen(route = "auth/sign_up/user_address")
    object RegisterNickname: AuthScreen(route = "auth/sign_up/nickname")
    object RegisterPreferences: AuthScreen(route = "auth/sign_up/preferences")
}

sealed class HomeScreen(val route : String){
    object Home : HomeScreen(route = "home/home")
    object Guide : HomeScreen(route = "home/guide")
}

sealed class MarketScreen(val route : String){
    object Home : MarketScreen(route = "market/home")

    object Category : MarketScreen(route = "market/category/{categoryId}"){
        fun passCategoryId(id: Int): String{
            return "market/category/$id"
        }
    }
}
sealed class BasketScreen(val route : String){
    object Home : BasketScreen(route = "basket/home")
}
sealed class RecipeScreen(val route : String){
    object Home : RecipeScreen(route = "recipes/home")
    object RecipeList: RecipeScreen("recipes/list?category={category}&ownerType={ownerType}") {
        fun passQuery(category: Int? = null, ownerType: String? = null): String {
            return "recipes/list?category=$category&ownerType=$ownerType"
        }
    }
}
sealed class MyScreen(val route : String){
    object Home : MyScreen(route = "my/home")
    object Like : MyScreen(route = "my/like")
    object Scrap : MyScreen(route = "my/scrap")
    object Myrecipe : MyScreen(route = "my/myrecipe")
    object Shopping : MyScreen(route = "my/shopping")
    object FriendList : MyScreen(route = "my/friendlist")
    object RecipeWrite : MyScreen(route = "my/recipewrite")
}

sealed class DrawerScreen(val route : String){
    //공지사항
    object Notice : DrawerScreen(route = "drawer/notice")
    //FAQ
    object FAQ : DrawerScreen(route = "drawer/faq")
    //오류문의 및 신고
    object Inquery : DrawerScreen(route = "drawer/inquery")
    //회원 정보
    object UserInfo : DrawerScreen(route = "drawer/userinfo")
    object UserInfoBasic : DrawerScreen(route="drawer/userinfo/basic")
    object UserInfoDetail : DrawerScreen(route="drawer/userinfo/detail")
    object UserInfoNickname : DrawerScreen(route="main/drawer/userinfo/nickname")

}


sealed class SharedScreen(val route : String){
    object DetailRecipe : SharedScreen(route = "shared/detail/{recipeId}")
    object Search : SharedScreen(route = "shared/search")

    object SearchRecipeCategory : SharedScreen(route = "shared/search/category")

}