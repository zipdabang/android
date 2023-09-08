package com.zipdabang.zipdabang_android.module.recipes.use_case

import androidx.datastore.core.DataStore
import com.zipdabang.zipdabang_android.common.Constants.TOKEN_NULL
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.recipes.data.banner.BannerImageItem
import com.zipdabang.zipdabang_android.module.recipes.domain.RecipeBanner
import com.zipdabang.zipdabang_android.module.recipes.domain.RecipeBannerRepository
import com.zipdabang.zipdabang_android.module.recipes.mapper.toRecipeBanner
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetRecipeBannerUseCase @Inject constructor(
    private val tokenDataStore: DataStore<Token>,
    private val recipeBannerRepository: RecipeBannerRepository
) {
    operator fun invoke(): Flow<Resource<List<BannerImageItem>>> = flow {
        val accessToken = ("Bearer " + tokenDataStore.data.first().accessToken)
        try {
            emit(Resource.Loading())
            val bannerData = recipeBannerRepository.getRecipeBanners(accessToken)?.toRecipeBanner()
            bannerData?.let {
                if (bannerData.isSuccessful) {
                    bannerData.recipeBanners?.let {
                        emit(
                            Resource.Success(
                                data = it,
                                code = bannerData.code,
                                message = bannerData.message
                            )
                        )
                    }
                } else {
                    emit(Resource.Error(message = bannerData.message))
                }
            }

        } catch (e: HttpException) {
            emit(Resource.Error(message = e.message ?: "unexpected http error"))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.message ?: "unexpected io error"))

        }
    }
}