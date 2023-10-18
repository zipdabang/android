package com.zipdabang.zipdabang_android.module.my.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import com.zipdabang.zipdabang_android.core.DeviceSize
import com.zipdabang.zipdabang_android.module.detail.recipe.common.DeviceScreenSize
import com.zipdabang.zipdabang_android.module.my.domain.repository.PagingCompleteRecipesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyRecipesViewModel @OptIn(ExperimentalPagingApi::class)
@Inject constructor(
    val completeRecipesRepository : PagingCompleteRecipesRepository
) : ViewModel(){

    @OptIn(ExperimentalPagingApi::class)
    var getCompleteRecipeItems = completeRecipesRepository.getCompleteRecipeItems().cachedIn(viewModelScope)

    @OptIn(ExperimentalPagingApi::class)
    fun refresh(){
        getCompleteRecipeItems = completeRecipesRepository.getCompleteRecipeItems().cachedIn(viewModelScope)
    }


}