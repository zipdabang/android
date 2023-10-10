package com.zipdabang.zipdabang_android.module.drawer.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import androidx.paging.compose.collectAsLazyPagingItems
import com.zipdabang.zipdabang_android.module.drawer.data.remote.reporterror.PagingReportRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ErrorListViewModel @OptIn(ExperimentalPagingApi::class)
@Inject constructor(
    private val repository: PagingReportRepository
) : ViewModel() {



    @OptIn(ExperimentalPagingApi::class)
    val inqueryItems = repository.getAllItems().cachedIn(viewModelScope)

}