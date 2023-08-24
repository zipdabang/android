package com.zipdabang.zipdabang_android.module.market.data

import com.zipdabang.zipdabang_android.module.market.data.marketCategory.CategoryDto
import com.zipdabang.zipdabang_android.module.market.data.marketMain.RecentDto
import com.zipdabang.zipdabang_android.module.market.domain.MarketRepository
import javax.inject.Inject

class MarketRepositoryImpl @Inject constructor(
    private val api: MarketApi
) : MarketRepository {

    override suspend fun getRecentItmes(token: String?): RecentDto {
        return api.getRecentItems(token)
    }

    override suspend fun getCategoryItems(token: String?, pageIndex: Int, categoryId: Int): CategoryDto {
       return api.getCategoryList(token,pageIndex, categoryId)
    }
}