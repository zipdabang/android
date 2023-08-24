package com.zipdabang.zipdabang_android.module.market.data.marketCategory

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.zipdabang.zipdabang_android.core.Paging3Database
import com.zipdabang.zipdabang_android.core.data_store.proto.ProtoRepository
import com.zipdabang.zipdabang_android.module.market.data.MarketApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@ExperimentalPagingApi
class PagingRepository @Inject constructor(
    private val marketApi: MarketApi,
    private val paging3Database: Paging3Database,
    private val protoRepository: ProtoRepository
){
    fun getAllItems(categoryId: Int, tokens: String): Flow<PagingData<Category_Product>>{
       val pagingSourceFactory = { paging3Database.CategoryDao().getAllItem()}
        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = MarketRemoteMediator(
                marketApi = marketApi,
                paging3Database = paging3Database,
                categoryId = categoryId,
                token = tokens
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

}