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
import com.zipdabang.zipdabang_android.module.my.data.remote.otherinfo.OtherRecipe
import com.zipdabang.zipdabang_android.module.my.data.remote.otherinfo.OtherRecipeListPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class PagingOtherRecipeListRepository @Inject constructor(
    private val myApi: MyApi,
    private val tokenDataStore : DataStore<Token>
) {
    fun getCompleteRecipeItems(memberId : Int) : Flow<PagingData<OtherRecipe>> {

        return Pager(
            config = PagingConfig(pageSize = Constants.ITEMS_PER_PAGE),
            pagingSourceFactory = {
                OtherRecipeListPagingSource(tokenDataStore,myApi,memberId)
            }
        ).flow
    }
}