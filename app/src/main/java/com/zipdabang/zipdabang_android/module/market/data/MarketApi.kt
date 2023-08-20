package com.zipdabang.zipdabang_android.module.market.data

import retrofit2.http.GET
import retrofit2.http.Header

interface MarketApi {


    @GET("/market/recent-items")
    suspend fun getRecentItems(
        @Header("token") token : String?
    ) : RecentDto

}