package com.zipdabang.zipdabang_android.module.search.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import com.zipdabang.zipdabang_android.module.search.data.dto.recipecategory.PagingSearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchCategoryViewModel @OptIn(ExperimentalPagingApi::class)
@Inject constructor(
    private val repository: PagingSearchRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel(){

    private var _categoryId = mutableStateOf(0)
    var categoryId = _categoryId

    private var _keyword= mutableStateOf("")
    var keyword = _keyword

    init{
        categoryId .value= savedStateHandle.get<Int>("categoryId")!!
        keyword.value = savedStateHandle.get<String>("keyword").toString()
        Log.e("searchtest_categoryId",categoryId.value.toString())
        Log.e("searchtest_keyword",keyword.value)
    }



    @OptIn(ExperimentalPagingApi::class)
    val getSearchRecipeCategoryItems = repository.getAllItems(categoryId.value,keyword.value).cachedIn(viewModelScope)



}