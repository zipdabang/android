package com.zipdabang.zipdabang_android.module.home.domain.usecase

import android.util.Log
import com.zipdabang.zipdabang_android.common.HomeResource
import com.zipdabang.zipdabang_android.common.MarketResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> HomeApiCall(apiCall: suspend  () -> T) : Flow<HomeResource<T>> = flow{
    try {
        Log.e("Api","ApiCall")
        emit(HomeResource.HomeLoading(true))
        Log.e("Api","ApiCallLoading")
        val data = apiCall()
        emit(HomeResource.HomeSuccess(data))
    } catch (e: HttpException) {
        emit(HomeResource.HomeError(e.localizedMessage ?: "An unexpected error occurred"))
    } catch (e: IOException) {
        emit(HomeResource.HomeError("Couldn't reach the server. Check your internet connection."))
    }
}