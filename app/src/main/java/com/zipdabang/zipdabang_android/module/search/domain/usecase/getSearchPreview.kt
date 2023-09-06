package com.zipdabang.zipdabang_android.module.search.domain.usecase

import androidx.datastore.core.DataStore
import com.zipdabang.zipdabang_android.common.MarketResource
import com.zipdabang.zipdabang_android.common.SearchResource
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.market.data.marketMain.RecentDto
import com.zipdabang.zipdabang_android.module.search.data.dto.SearchDto
import com.zipdabang.zipdabang_android.module.search.domain.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class GetSearchPreviewUseCase @Inject constructor(
    private val repository: SearchRepository,
    private val tokenDataStore : DataStore<Token>
) {


    operator fun invoke(keyword: String?) : Flow<SearchResource<SearchDto>> = flow {
        try{
            emit(SearchResource.SearchLoading(true))
            val accessToken = ("Bearer " + tokenDataStore.data.first().accessToken)
            val data = repository.getSearchPreview(accessToken,keyword)!!
            emit(SearchResource.SearchSuccess(data))
        } catch (e : HttpException){
            emit(SearchResource.SearchError(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e : IOException){
            emit(SearchResource.SearchError("Couldn't reach server. Check your internal code"))
        }

    }

}