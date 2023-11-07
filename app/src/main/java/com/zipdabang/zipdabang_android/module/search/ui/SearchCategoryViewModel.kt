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
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.core.Paging3Database
import com.zipdabang.zipdabang_android.module.my.data.remote.otherinfo.OtherRecipe
import com.zipdabang.zipdabang_android.module.recipes.use_case.ToggleLikeItemUseCase
import com.zipdabang.zipdabang_android.module.recipes.use_case.ToggleScrapItemUseCase
import com.zipdabang.zipdabang_android.module.search.data.dto.common.SearchRecipe
import com.zipdabang.zipdabang_android.module.search.data.dto.recipecategory.PagingSearchRepository
import com.zipdabang.zipdabang_android.module.search.domain.usecase.GetSearchCountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchCategoryViewModel @OptIn(ExperimentalPagingApi::class)
@Inject constructor(
    private val repository: PagingSearchRepository,
    private val savedStateHandle: SavedStateHandle,
    private val countUseCase: GetSearchCountUseCase,
    private val toggleLikeItemUseCase: ToggleLikeItemUseCase,
    private val toggleScrapItemUseCase: ToggleScrapItemUseCase
): ViewModel(){

    private var _categoryId = mutableStateOf(0)
    var categoryId = _categoryId

    private var _keyword= mutableStateOf("")
    var keyword = _keyword

    private var _order = mutableStateOf("latest")
    var order = _order

    private var _searchState = mutableStateOf(SearchState())
    val searchState = _searchState


    private val _recipeList = MutableStateFlow<PagingData<SearchRecipe>>(PagingData.empty())
    val recipeList = _recipeList

    init{
        categoryId.value= savedStateHandle.get<Int>("categoryId")!!
        keyword.value = savedStateHandle.get<String>("keyword").toString()
        getSearchRecipeList()
        getCount()
    }

    fun getCount(){
        countUseCase(categoryId.value,keyword.value).onEach {
            result ->
            when (result) {
                is Resource.Success -> {
                    if (result.data?.isSuccess == true) {
                        val count = result.data.result
                        Log.e("searchResult", result.data.result.toString())
                        _searchState.value = SearchState(count = count)
                    }
                }

                is Resource.Error -> {
                    _searchState.value = SearchState(
                        isError = true,
                        error = result.message.toString()
                    )
                }

                is Resource.Loading-> {
                    _searchState.value = SearchState(isLoading = true)
                }

            }




        }.launchIn(viewModelScope)
    }

    @OptIn(ExperimentalPagingApi::class)
    fun getSearchRecipeList(){
        viewModelScope.launch {
            repository.getAllItems(categoryId.value,order.value,keyword.value).cachedIn(viewModelScope).collect{
                _recipeList.value = it
            }
        }
    }

    suspend fun toggleItemLike(recipeId: Int): Boolean = withContext(Dispatchers.IO) {
        toggleLikeItemUseCase(recipeId)
    }

    suspend fun toggleItemScrap(recipeId: Int): Boolean = withContext(Dispatchers.IO) {
            toggleScrapItemUseCase(recipeId)
    }




}