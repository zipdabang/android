package com.zipdabang.zipdabang_android.module.drawer.data.remote.reporterror

import androidx.datastore.core.DataStore
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.zipdabang.zipdabang_android.core.Paging3Database
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.drawer.data.remote.DrawerApi
import com.zipdabang.zipdabang_android.module.search.data.dto.common.SearchRecipe
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class PagingReportRepository @Inject constructor(
    private val reportApi: DrawerApi,
    private val paging3Database: Paging3Database,
    private val tokenDataStore : DataStore<Token>
){
    fun getAllItems(): Flow<PagingData<InqueryDB>> {

       val pagingSourceFactory = { paging3Database.reportDao().getAllItem()}
        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = ReportListMediator(
                reportApi = reportApi,
                paging3Database = paging3Database,
                tokenDataStore = tokenDataStore
                ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

}