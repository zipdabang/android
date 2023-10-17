package com.zipdabang.zipdabang_android.module.drawer.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.module.drawer.domain.usecase.GetNoticeUseCase
import com.zipdabang.zipdabang_android.module.drawer.ui.state.NoticeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class NoticeViewModel @Inject constructor(
    private val noticeUseCase: GetNoticeUseCase
) :ViewModel(){


    private val _noticeState = mutableStateOf(NoticeState())
    val noticeState = _noticeState


    init {
        getNotice()
    }

    fun getNotice(){

        noticeUseCase().onEach {

            result ->
            when(result){

                is Resource.Loading -> {
                    _noticeState.value = NoticeState(isLoading = true)
                }

                is Resource.Success -> {
                    _noticeState.value = NoticeState(isSuccess = true, noticeList = result.data!!.result!!.noticeList)
                }
                is Resource.Error -> {
                    _noticeState.value = NoticeState(isSuccess = false, isError = true)
                    Log.e("Error in Notice Api",result.message.toString())
                }
            }



        }.launchIn(viewModelScope)
    }

}