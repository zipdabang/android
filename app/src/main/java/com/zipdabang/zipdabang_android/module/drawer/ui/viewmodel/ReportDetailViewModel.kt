package com.zipdabang.zipdabang_android.module.drawer.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.module.drawer.domain.usecase.GetReportDetailUseCase
import com.zipdabang.zipdabang_android.module.drawer.ui.state.ReportDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ReportDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
   val getReportDetailUseCase: GetReportDetailUseCase
): ViewModel(){

    private val _reportDetailState = mutableStateOf(ReportDetailState())
    val reportDetailState = _reportDetailState

    init {
       val id = savedStateHandle.get<Int>("reportId")
        id?.let{
            getReportDetail(it)
        }
    }


     fun getReportDetail(reportId : Int){

        getReportDetailUseCase(reportId).onEach {

            result -> when(result) {

            is Resource.Success -> {

                _reportDetailState.value = ReportDetailState(isSuccess = true, reportDetail = result.data!!.result, isLoading = false)

            }

            is Resource.Error -> {
                _reportDetailState.value = ReportDetailState(isError = true)
                Log.e("error in ReportDetail",result.message.toString())

            }

            is Resource.Loading -> {
                _reportDetailState.value = ReportDetailState(isLoading = true)


            }
        }



        }.launchIn(viewModelScope)


    }
}