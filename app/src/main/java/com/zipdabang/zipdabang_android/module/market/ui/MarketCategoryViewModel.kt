package com.zipdabang.zipdabang_android.module.market.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import com.zipdabang.zipdabang_android.core.data_store.ProtoRepository
import com.zipdabang.zipdabang_android.core.data_store.Token
import com.zipdabang.zipdabang_android.module.market.data.marketCategory.PagingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@ExperimentalPagingApi
@HiltViewModel
class MarketCategoryViewModel @Inject constructor(
    private val repository: PagingRepository,
    private val protoRepository: ProtoRepository
): ViewModel() {

    var token: String? = null
    private val categoryId: Int = 0

    private val accessToken: Flow<Token>
        get() {
            return protoRepository.tokens
        }



   /* val getMarketCategoryItems :  Flow<PagingData<Category_Product>>  =
        {
        viewModelScope.launch {
            accessToken
                .map { it.accessToken }
                .collect {
                    token = it
                }
            token?.let {
                repository.getAllItems(categoryId, token!!)
            }
        }
    } */

}