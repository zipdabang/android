package com.zipdabang.zipdabang_android.module.home.domain.usecase

import android.util.Log
import androidx.datastore.core.DataStore
import com.zipdabang.zipdabang_android.common.Constants
import com.zipdabang.zipdabang_android.common.HomeResource
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.home.data.banner.HomeBannerDto
import com.zipdabang.zipdabang_android.module.home.data.bestrecipe.BestRecipeDto
import com.zipdabang.zipdabang_android.module.home.domain.HomeRepository
import com.zipdabang.zipdabang_android.module.home.ui.HomeRecipeState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetBestRecipe @Inject constructor(
    private val repository: HomeRepository,
    private val datastore : DataStore<Token>
) {
    operator fun invoke() : Flow<HomeResource<BestRecipeDto>> = flow{

        val accessToken = datastore.data.first().accessToken ?: Constants.TOKEN_NULL
        val token = "Bearer " + accessToken
        try {
            emit(HomeResource.HomeLoading(true))
            val data = repository.getBestRecipes(token)
            emit(HomeResource.HomeSuccess(data))
        } catch (e: HttpException) {
            emit(HomeResource.HomeError(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(HomeResource.HomeError("Couldn't reach the server. Check your internet connection."))
        }
    }

}