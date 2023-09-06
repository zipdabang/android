package com.zipdabang.zipdabang_android.module.market.domain.use_case.get_recentItem

import com.zipdabang.zipdabang_android.common.MarketResource
import com.zipdabang.zipdabang_android.module.market.data.marketMain.RecentDto
import com.zipdabang.zipdabang_android.module.market.domain.MarketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetRecentUseCase  @Inject constructor(
    private val repository: MarketRepository
) {
    operator fun invoke(token : String) : Flow<MarketResource<RecentDto>> = flow {
        try{
            emit(MarketResource.MarketLoading(true))
            val data = repository.getRecentItmes(token)
            emit(MarketResource.MarketSuccess(data))
    } catch (e : HttpException){
        emit(MarketResource.MarketError(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e : IOException){
            emit(MarketResource.MarketError("Couldn't reach server. Check your internal code"))
        }

    }

}