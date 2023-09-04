package com.zipdabang.zipdabang_android.module.home.domain.usecase

import android.media.session.MediaSession.Token
import android.util.Log
import androidx.datastore.core.DataStore
import com.zipdabang.zipdabang_android.common.Constants
import com.zipdabang.zipdabang_android.common.HomeResource
import com.zipdabang.zipdabang_android.module.home.data.banner.HomeBannerDto
import com.zipdabang.zipdabang_android.module.home.domain.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class GetHomeBanner @Inject constructor(
    private val repository: HomeRepository,
    private val datastore : DataStore<com.zipdabang.zipdabang_android.core.data_store.proto.Token>
) {
    operator fun invoke() : Flow<HomeResource<HomeBannerDto>> = flow{

        val accessToken = datastore.data.first().accessToken ?: Constants.TOKEN_NULL
        Log.e("token in invoke",accessToken.toString())
        try {
            emit(HomeResource.HomeLoading(true))
            val data = repository.getHomeBanner(accessToken)
            emit(HomeResource.HomeSuccess(data))
        } catch (e: HttpException) {
            emit(HomeResource.HomeError(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(HomeResource.HomeError("Couldn't reach the server. Check your internet connection."))
        }
    }

}