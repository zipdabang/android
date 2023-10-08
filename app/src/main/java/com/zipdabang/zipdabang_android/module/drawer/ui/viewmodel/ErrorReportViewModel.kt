package com.zipdabang.zipdabang_android.module.drawer.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.drawer.ui.state.ReportState
import com.zipdabang.zipdabang_android.module.drawer.domain.usecase.PostReportUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class ErrorReportViewModel @Inject constructor(
    private val reportUseCase: PostReportUseCase,
    private val dataStore : DataStore<Token>
)
: ViewModel() {

    private var _reportState = mutableStateOf(ReportState())
    val reportState = _reportState

    fun postReport(email:String, title: String, body: String, imageList: List<MultipartBody.Part>, isSuccess : ()-> Unit){
        Log.e("report Api",imageList.size.toString())
//        val requestEmail : RequestBody= RequestBody.create("text/plain".toMediaTypeOrNull(),email)
//        val requestTitle = RequestBody.create("text/plain".toMediaTypeOrNull(),title)
//        val requestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),body)

        reportUseCase(email, title, body, imageList).onEach {
            result ->
            when(result) {
                is Resource.Success -> {
                    if(result.data?.isSuccess == true){
                        _reportState.value= ReportState(
                            isSuccess = true,
                            isLoading = false
                        )

                        isSuccess()

                        Log.e("report Api in Success",result.data.code.toString())
                    }
                }
                is Resource.Error -> {
                    _reportState.value = ReportState(
                        isError = true,
                        error = result.message.toString()
                    )
                    Log.e("report Api in Error","code :${result.code} message : ${result.message.toString()}")

                }

                is Resource.Loading->{
                    _reportState.value = ReportState(
                        isLoading = true
                    )
                }
            }
        }.launchIn(viewModelScope)

    }

}