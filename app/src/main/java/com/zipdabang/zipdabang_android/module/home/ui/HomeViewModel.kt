package com.zipdabang.zipdabang_android.module.home.ui

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zipdabang.zipdabang_android.common.Constants
import com.zipdabang.zipdabang_android.common.HomeResource
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.home.domain.usecase.GetBestRecipe
import com.zipdabang.zipdabang_android.module.home.domain.usecase.GetHomeBanner
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val datastore : DataStore<Token>,
    private val getHomeBanner : GetHomeBanner,
    private  val getBestRecipe: GetBestRecipe
) :ViewModel(){

    private val _bannerState = mutableStateOf(HomeBannerState())
    var bannerState = _bannerState

    private val _recipeState =  mutableStateOf(HomeRecipeState())
    var recipeState = _recipeState
    fun getBannerList() {
        viewModelScope.launch {

            val accessToken = datastore.data.first().accessToken ?: Constants.TOKEN_NULL
            accessToken.let {
                getHomeBanner(it).onEach { result ->
                    when (result) {

                        is HomeResource.HomeSuccess ->{
                            if(result.data?.isSuccess == true){
                                val bannerlist= result.data.result.bannerList
                                _bannerState.value = HomeBannerState(
                                    bannerList = result.data.result.bannerList,
                                    isLoading = false)
                            }else{
                                Log.e("Market Api Error", result.data!!.message)
                            }
                        }

                        is HomeResource.HomeError ->{
                            _bannerState.value = HomeBannerState(
                                error = result.data?.message ?: "An unexpected error occured",
                                isLoading = true
                            )
                        }

                        is HomeResource.HomeLoading-> {
                            _bannerState.value = HomeBannerState(isLoading = true)
                        }

                    }

                }

            }


        }
    }


    fun getBestRecipe(){
        viewModelScope.launch {

            val accessToken = datastore.data.first().accessToken ?: Constants.TOKEN_NULL
            accessToken.let {
                getBestRecipe(it).onEach { result ->
                    when (result) {

                        is HomeResource.HomeSuccess ->{
                            if(result.data?.isSuccess == true){
                                _recipeState.value = HomeRecipeState(
                                    recipeList = result.data.result.recipeList,
                                    isLoading = false)
                            }else{
                                Log.e("Market Api Error", result.data!!.message)
                            }
                        }

                        is HomeResource.HomeError ->{
                            _recipeState.value = HomeRecipeState(
                                error = result.data?.message ?: "An unexpected error occured"
                            )
                        }

                        is HomeResource.HomeLoading-> {
                            _recipeState.value = HomeRecipeState(isLoading = true)
                        }

                    }

                }

            }


        }

    }

}