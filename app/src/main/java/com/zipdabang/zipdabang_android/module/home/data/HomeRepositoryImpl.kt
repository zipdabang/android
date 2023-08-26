package com.zipdabang.zipdabang_android.module.home.data

import com.zipdabang.zipdabang_android.module.home.data.bestreciepe.BestRecipeDto
import com.zipdabang.zipdabang_android.module.home.domain.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject  constructor(
   private val homeApi: HomeApi
) : HomeRepository {

    override suspend fun getBestRecipes(token: String): BestRecipeDto {
       return homeApi.getBestRecipes(token)
    }


}