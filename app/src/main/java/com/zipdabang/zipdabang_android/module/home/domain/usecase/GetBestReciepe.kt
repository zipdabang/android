package com.zipdabang.zipdabang_android.module.home.domain.usecase

import com.zipdabang.zipdabang_android.common.HomeResource
import com.zipdabang.zipdabang_android.module.home.data.bestreciepe.BestRecipeDto
import com.zipdabang.zipdabang_android.module.home.domain.HomeRepository
import com.zipdabang.zipdabang_android.module.market.domain.use_case.get_recentItem.MarketApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetBestRecipe @Inject constructor(
  private val repository: HomeRepository
) {
    operator fun invoke(token : String) : Flow<HomeResource<BestRecipeDto>> = flow{
       HomeApiCall {
            repository.getBestRecipes(token)
        }
    }


}