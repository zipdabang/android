package com.zipdabang.zipdabang_android.module.my.domain.repository

import androidx.datastore.core.DataStore
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.zipdabang.zipdabang_android.common.Constants
import com.zipdabang.zipdabang_android.core.Paging3Database
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.my.data.remote.MyApi
import com.zipdabang.zipdabang_android.module.my.data.remote.myrecipes.complete.CompleteRecipe
import com.zipdabang.zipdabang_android.module.my.data.remote.myrecipes.complete.CompleteRecipeMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PagingCompleteRecipesRepository @Inject constructor(
    private val myApi: MyApi,
    private val paging3Database: Paging3Database,
    private val tokenDataStore : DataStore<Token>
) {
    @OptIn(ExperimentalPagingApi::class)
    fun getCompleteRecipeItems() : Flow<PagingData<CompleteRecipe>> {
        val pagingSourceFactory = {
            paging3Database.completeRecipesDao().getAllItem()
        }
        return Pager(
            config = PagingConfig(pageSize = Constants.ITEMS_PER_PAGE),
            remoteMediator = CompleteRecipeMediator(
                myApi = myApi,
                paging3Database = paging3Database,
                tokenDataStore = tokenDataStore
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}