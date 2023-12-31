package com.zipdabang.zipdabang_android.module.home.domain.usecase

import android.media.session.MediaSession.Token
import android.util.Log
import androidx.datastore.core.DataStore
import com.zipdabang.zipdabang_android.common.Constants
import com.zipdabang.zipdabang_android.common.HomeResource
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.common.getErrorCode
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
    operator fun invoke() : Flow<Resource<HomeBannerDto>> = flow {

        val accessToken = datastore.data.first().accessToken ?: Constants.TOKEN_NULL
        val token = "Bearer " + accessToken
        Log.e("token",token.toString())
        try {
            emit(Resource.Loading())
            val data = repository.getHomeBanner(token)
            emit(Resource.Success(data = data,code = data.code, message = data.message))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()
            val errorCode = errorBody?.getErrorCode()
            errorCode?.let {
                emit(
                    Resource.Error(
                        message = e.response()?.errorBody().toString(),
                        code = errorCode
                    )
                )
                return@flow
            }
            emit(Resource.Error(message = e.message ?: "unexpected http error"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach the server. Check your internet connection."))
        }
    }

}

