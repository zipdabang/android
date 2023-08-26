package com.zipdabang.zipdabang_android.module.home.domain.usecase

import com.zipdabang.zipdabang_android.common.HomeResource
import com.zipdabang.zipdabang_android.module.home.data.banner.HomeBannerDto
import com.zipdabang.zipdabang_android.module.home.domain.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetHomeBanner @Inject constructor(
    private val repository: HomeRepository
) {
    operator fun invoke(token : String) : Flow<HomeResource<HomeBannerDto>> = flow{
        HomeApiCall {
            repository.getHomeBanner(token)
        }
    }

}