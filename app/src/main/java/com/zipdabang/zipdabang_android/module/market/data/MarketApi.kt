package com.zipdabang.zipdabang_android.module.market.data

import androidx.compose.runtime.MutableState
import com.zipdabang.zipdabang_android.module.market.data.marketCategory.CategoryDto
import com.zipdabang.zipdabang_android.module.market.data.marketMain.RecentDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MarketApi {

    @GET("/market/recent-items")
    suspend fun getRecentItems(
        @Header("token") token : String?
    ) : RecentDto


    @GET("/market/recent-items")
    suspend fun getCategoryList(
        @Header("token") token: String?,
        @Query("pageIndex") pageIndex: Int,
        @Query("categoryId") categoryId: Int
    ) : CategoryDto


}