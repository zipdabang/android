package com.zipdabang.zipdabang_android.module.market.domain

import com.zipdabang.zipdabang_android.module.market.data.RecentDto

interface MarketRepository {

    suspend fun getRecentItmes(token : String?) : RecentDto


}