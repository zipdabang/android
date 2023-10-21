package com.zipdabang.zipdabang_android.module.my.ui.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.following.Follower
import com.zipdabang.zipdabang_android.module.my.data.remote.otherinfo.OtherRecipe
import com.zipdabang.zipdabang_android.module.my.domain.repository.PagingOtherRecipeListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
@HiltViewModel
class OtherRecipeListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val otherRecipeListRepository: PagingOtherRecipeListRepository
) : ViewModel() {
    private val _recipeList = MutableStateFlow<PagingData<OtherRecipe>>(PagingData.empty())
    val recipeList = _recipeList
    init {
       val memeberId= savedStateHandle.get<Int>("userId")
        Log.e("userId",memeberId.toString())
        memeberId?.let {
            getOtherRecipeList(it)
        }

    }

    @OptIn(ExperimentalPagingApi::class)
    fun getOtherRecipeList(memberId : Int){
        viewModelScope.launch {
            otherRecipeListRepository.getCompleteRecipeItems(memberId).cachedIn(viewModelScope).collect{
                _recipeList.value = it
            }
        }
    }



}