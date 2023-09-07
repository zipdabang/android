package com.zipdabang.zipdabang_android.module.search.data.dto.recipecategory

import androidx.datastore.core.DataStore
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.zipdabang.zipdabang_android.core.Paging3Database
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.search.data.SearchApi
import com.zipdabang.zipdabang_android.module.search.data.dto.common.SearchRecipe
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class PagingSearchRepository @Inject constructor(
    private val searchApi: SearchApi,
    private val paging3Database: Paging3Database,
    private val tokenDataStore : DataStore<Token>
){
    fun getAllItems(categoryId: Int,keyword: String): Flow<PagingData<SearchRecipe>> {
       val pagingSourceFactory = { paging3Database.SearchRecipeDao().getAllItem()}
        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = SearchRecipeCategoryMediator(
                searchApi= searchApi,
                paging3Database = paging3Database,
                categoryId = categoryId,
                searchText = keyword,
                tokenDataStore = tokenDataStore
                ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

}