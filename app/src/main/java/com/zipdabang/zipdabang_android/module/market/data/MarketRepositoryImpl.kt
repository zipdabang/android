package com.zipdabang.zipdabang_android.module.market.data

import com.zipdabang.zipdabang_android.module.market.domain.MarketRepository
import javax.inject.Inject

class MarketRepositoryImpl @Inject constructor(
    private val api: MarketApi
) : MarketRepository {

    override suspend fun getRecentItmes(token: String?): RecentDto {
        return api.getRecentItems(token)
    }
}