package com.zipdabang.zipdabang_android.module.my.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zipdabang.zipdabang_android.core.DeviceSize
import com.zipdabang.zipdabang_android.module.detail.recipe.common.DeviceScreenSize
import com.zipdabang.zipdabang_android.module.my.data.remote.myrecipes.complete.CompleteRecipe
import com.zipdabang.zipdabang_android.module.my.data.remote.myrecipes.temp.TempRecipe
import com.zipdabang.zipdabang_android.module.my.domain.repository.PagingCompleteRecipesRepository
import com.zipdabang.zipdabang_android.module.my.domain.repository.PagingTempRecipesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyRecipesViewModel @OptIn(ExperimentalPagingApi::class)
@Inject constructor(
    val completeRecipesRepository : PagingCompleteRecipesRepository,
    val tempRecipesRepository: PagingTempRecipesRepository,
) : ViewModel(){

    private val _completeRecipeItems = MutableStateFlow<PagingData<CompleteRecipe>>(PagingData.empty())
    val completeRecipeItems = _completeRecipeItems

    private val _tempRecipeItems = MutableStateFlow<PagingData<TempRecipe>>(PagingData.empty())
    val tempRecipeItems = _tempRecipeItems

    @OptIn(ExperimentalPagingApi::class)
    fun getCompleteRecipeItems(){
        viewModelScope.launch {
            completeRecipesRepository.getCompleteRecipeItems().cachedIn(viewModelScope).collect{
                _completeRecipeItems.value = it
            }
        }
    }

    fun getTempRecipeItems(){
        viewModelScope.launch {
            tempRecipesRepository.getTempRecipeItems().cachedIn(viewModelScope).collect{
                _tempRecipeItems.value = it
            }
        }
    }


}