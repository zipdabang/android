package com.zipdabang.zipdabang_android.module.market.ui

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zipdabang.zipdabang_android.common.MarketResource
import com.zipdabang.zipdabang_android.core.data_store.proto.ProtoRepository
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.market.domain.use_case.get_recentItem.GetRecentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RecentMarketViewMdoel @Inject constructor(
    private val getRecentUseCase: GetRecentUseCase,
    private val protoRepository: ProtoRepository
) : ViewModel() {

    private val _state = mutableStateOf(MainMarketState())
    val state : State<MainMarketState> = _state
    var token : String? = null
    private val accessToken : Flow<Token>
        get(){
            return protoRepository.tokens
        }
    init {
        getMainMarket()
    }
    private fun getMainMarket(){

        viewModelScope.launch {
            accessToken
                .map { it.accessToken }
                .collect {
                    token = it
                }

            token?.let {
                getRecentUseCase(it).onEach { result ->
                    when (result) {

                        is MarketResource.MarketSuccess ->{
                            if(result.data?.isSuccess == true){
                                _state.value = MainMarketState(
                                    categoryList = result.data.result.productCategoryList,
                                    recentProductList = result.data.result.productList)
                            }else{
                                Log.e("Market Api Error", result.data!!.message)
                            }
                        }

                        is MarketResource.MarketError ->{
                            _state.value = MainMarketState(
                                error = result.data?.message ?: "An unexpected error occured"
                            )
                        }

                        is MarketResource.MarketLoading -> {
                            _state.value = MainMarketState(isLoading = true)
                        }

                    }

                }
            }
        }


    }





}