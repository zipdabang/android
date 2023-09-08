package com.zipdabang.zipdabang_android.module.market.domain.use_case.get_recentItem

import com.zipdabang.zipdabang_android.common.MarketResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException


suspend fun <T> MarketApiCall(apiCall: suspend  () -> T) : Flow<MarketResource<T>> = flow{
    try {
        emit(MarketResource.MarketLoading(true))
        val data = apiCall()
        emit(MarketResource.MarketSuccess(data))
    } catch (e: HttpException) {
        emit(MarketResource.MarketError(e.localizedMessage ?: "An unexpected error occurred"))
    } catch (e: IOException) {
        emit(MarketResource.MarketError("Couldn't reach the server. Check your internet connection."))
    }
}