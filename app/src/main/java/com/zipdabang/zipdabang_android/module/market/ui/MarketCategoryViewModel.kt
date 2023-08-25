package com.zipdabang.zipdabang_android.module.market.ui

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.zipdabang.zipdabang_android.core.data_store.ProtoRepository
import com.zipdabang.zipdabang_android.core.data_store.Token
import com.zipdabang.zipdabang_android.module.market.data.marketCategory.Category_Product
import com.zipdabang.zipdabang_android.module.market.data.marketCategory.PagingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@ExperimentalPagingApi
@HiltViewModel
class MarketCategoryViewModel @Inject constructor(
    repository: PagingRepository,
): ViewModel() {


    private var categoryId: Int = 0

    fun setCategoryId(id : Int){
        categoryId = id
    }


    val getMarketCategoryItems = repository.getAllItems(categoryId).cachedIn(viewModelScope)


}