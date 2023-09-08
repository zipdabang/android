package com.zipdabang.zipdabang_android.module.search.ui
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zipdabang.zipdabang_android.common.HomeResource
import com.zipdabang.zipdabang_android.common.SearchResource
import com.zipdabang.zipdabang_android.module.home.ui.HomeBannerState
import com.zipdabang.zipdabang_android.module.home.ui.HomeRecipeState
import com.zipdabang.zipdabang_android.module.search.domain.SearchRepository
import com.zipdabang.zipdabang_android.module.search.domain.usecase.GetSearchPreviewUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository,
    private val getSearchUseCase : GetSearchPreviewUseCase
) : ViewModel() {

    private var _searchState = mutableStateOf(SearchState())
    val searchState = _searchState


    private var _searchText= mutableStateOf("")
    var searchText= _searchText



    fun getSearchList() {
        Log.e("getSearchList","get")
        getSearchUseCase(searchText.value).onEach { result ->
            when (result) {
                is SearchResource.SearchSuccess -> {
                    if (result.data?.isSuccess == true) {
                        val searchList = result.data.result.recipeList
                        Log.e("searchResult", result.data.result.toString())
                        _searchState.value = SearchState(
                            searchList = searchList,
                            isLoading = false
                        )
                    } else {
                        Log.e("Search Api Error", result.data!!.message)
                    }
                }

                is SearchResource.SearchError -> {
                    _searchState.value = SearchState(
                        isError = true,
                        error = result.message ?: "An unexpected error occured"
                    )
                }

                is SearchResource.SearchLoading -> {
                    _searchState.value = SearchState(isLoading = true)
                }


            }

        }.launchIn(viewModelScope)


    }
}