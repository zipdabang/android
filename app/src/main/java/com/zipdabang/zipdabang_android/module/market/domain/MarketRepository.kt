package com.zipdabang.zipdabang_android.module.market.domain

import com.zipdabang.zipdabang_android.module.market.data.marketCategory.CategoryDto
import com.zipdabang.zipdabang_android.module.market.data.marketCategory.Category_Product
import com.zipdabang.zipdabang_android.module.market.data.marketMain.RecentDto
import retrofit2.http.Query

interface MarketRepository {

    suspend fun getRecentItmes(token : String?) : RecentDto

    suspend fun getCategoryItems(token : String?, pageIndex : Int, categoryId : Int) : CategoryDto





}