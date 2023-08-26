package com.zipdabang.zipdabang_android.module.home.domain
import com.zipdabang.zipdabang_android.module.home.data.bestreciepe.BestRecipeDto

interface HomeRepository {

    suspend fun getBestRecipes(token : String) : BestRecipeDto

}