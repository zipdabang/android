package com.zipdabang.zipdabang_android.module.drawer.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.core.data_store.proto.CurrentPlatform
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.drawer.data.remote.quitdto.QuitRequest
import com.zipdabang.zipdabang_android.module.drawer.domain.usecase.PatchQuitReasonUseCase
import com.zipdabang.zipdabang_android.module.drawer.ui.quit.QuitState
import com.zipdabang.zipdabang_android.module.home.ui.HomeBannerState
import com.zipdabang.zipdabang_android.module.home.ui.HomeRecipeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class QuitViewModel @Inject constructor(
    val quitUseCase : PatchQuitReasonUseCase,
    val dataStore : DataStore<Token>
) : ViewModel() {
    private val _quitState = mutableStateOf(QuitState())
    var quitState = _quitState


    fun pathQuitReasonUseCase(reasonList : List<String>, feedBack: String, onDialogShow : ()-> Unit){

       val quitRequest  = QuitRequest(deregisterTypes = reasonList, feedback = feedBack)
        reasonList.forEach {
            Log.e("reasonList",it)
        }

        quitUseCase(deregisterTypes = reasonList,feedback = feedBack).onEach {
        result ->
            when(result){
                is Resource.Loading -> {
                    _quitState.value = QuitState(
                        isLoading = true
                    )
                }
                is Resource.Success -> {
                    if(result.data?.isSuccess == true){
                        _quitState.value = QuitState(
                            isLoading = false,
                            isSuccessful = true
                        )
                        result.code?.let { Log.e("quit", it.toString()) }

                        dataStore.updateData {
                            it.copy(
                                accessToken = null,
                                refreshToken = null,
                                platformToken = null,
                                platformStatus = CurrentPlatform.NONE
                            )
                        }
                    onDialogShow()

                    }
                }
                is Resource.Error ->{
                    Log.e("Quit Api in Error","code :${result.code} message : ${result.message.toString()}")
                    _quitState.value = QuitState(
                        isLoading = false,
                        isError = false,
                        error = result.message.toString()
                    )
                }


            }
        }.launchIn(viewModelScope)
    }

}