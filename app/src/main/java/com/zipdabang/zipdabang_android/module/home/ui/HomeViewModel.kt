package com.zipdabang.zipdabang_android.module.home.ui

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zipdabang.zipdabang_android.common.Constants
import com.zipdabang.zipdabang_android.common.HomeResource
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.home.domain.usecase.GetBestRecipe
import com.zipdabang.zipdabang_android.module.home.domain.usecase.GetHomeBanner
import com.zipdabang.zipdabang_android.module.recipes.use_case.ToggleLikeItemUseCase
import com.zipdabang.zipdabang_android.module.recipes.use_case.ToggleLikeUseCase
import com.zipdabang.zipdabang_android.module.recipes.use_case.ToggleScrapItemUseCase
import com.zipdabang.zipdabang_android.module.recipes.use_case.ToggleScrapUseCase
import com.zipdabang.zipdabang_android.module.recipes.use_case.ToggleScrapUseCase_Factory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeBanner : GetHomeBanner,
    private val getBestRecipes: GetBestRecipe,
    private val toggleItemLikeUseCase: ToggleLikeItemUseCase,
    private val toggleItemScrapUseCase: ToggleScrapItemUseCase,
) :ViewModel(){

    private val _bannerState = mutableStateOf(HomeBannerState())
    var bannerState = _bannerState

    private val _recipeState =  mutableStateOf(HomeRecipeState())
    var recipeState = _recipeState

    init {
        getBannerList()
        getBestRecipe()
    }

    suspend fun toggleItemLike(recipeId: Int): Boolean = withContext(Dispatchers.IO) {
        toggleItemLikeUseCase(recipeId)
    }

    suspend fun toggleItemScrap(recipeId: Int): Boolean = withContext(Dispatchers.IO) {
        toggleItemScrapUseCase(recipeId)
    }

    fun getBannerList() {
                getHomeBanner().onEach { result ->
                    when (result) {
                        is Resource.Success ->{
                            if(result.data?.isSuccess == true){
                                val bannerlist= result.data.result.bannerList
                                Log.e("Bannerresult",result.data.result.toString())
                                _bannerState.value = HomeBannerState(
                                    bannerList = bannerlist,
                                    isLoading = false)
                            }
                        }

                        is Resource.Error ->{
                            _bannerState.value = HomeBannerState(
                                isError = true,
                                error = result.message ?: "An unexpected error occured"
                            )
                            Log.e("Banner Api in Error","code :${result.code} message : ${result.message.toString()}")

                        }

                        is Resource.Loading-> {
                            _bannerState.value = HomeBannerState(isLoading = true)
                        }


                }

            }.launchIn(viewModelScope)



    }


    fun getBestRecipe(){

        getBestRecipes().onEach { result ->
                    when (result) {

                        is Resource.Success ->{
                            if(result.data?.isSuccess == true){
                                Log.e("Bannerresult",result.data.result.toString())
                                _recipeState.value =
                                    HomeRecipeState(
                                    recipeList = result.data.result.recipeList,
                                    isLoading = false
                                    )
                            }
                        }

                        is Resource.Error ->{
                            _recipeState.value = HomeRecipeState(
                                isError = true,
                                error = result.message.toString()
                            )
                            Log.e("Home Api in Error","code :${result.code} message : ${result.message.toString()}")

                        }

                        is Resource.Loading-> {
                            _recipeState.value = HomeRecipeState(isLoading = true)
                        }

                    }

                }.launchIn(viewModelScope)

            }



}