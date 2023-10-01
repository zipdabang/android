package com.zipdabang.zipdabang_android.module.drawer.ui.viewmodel

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.drawer.data.remote.reporterror.ReportState
import com.zipdabang.zipdabang_android.module.drawer.domain.usecase.PostReportUseCase
import com.zipdabang.zipdabang_android.module.search.ui.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.internal.EMPTY_REQUEST
import javax.inject.Inject

@HiltViewModel
class ErrorReportViewModel @Inject constructor(
    val reportUseCase: PostReportUseCase,
    val dataStore : DataStore<Token>
)
: ViewModel() {

    private var _reportState = mutableStateOf(ReportState())
    val reportState = _reportState

    fun postReport(email:String, title: String, body: String, imageList : MutableList<MultipartBody.Part>){
        val requestEmail : RequestBody= RequestBody.create("text/plain".toMediaTypeOrNull(),email)
        val requestTitle = RequestBody.create("text/plain".toMediaTypeOrNull(),title)
        val requestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),body)

        reportUseCase(requestEmail,requestTitle,requestBody,imageList).onEach {
            result ->
            when(result) {
                is Resource.Success -> {
                    if(result.data?.isSuccess == true){
                        _reportState.value= ReportState(
                            isSuccess = true,
                            isLoading = false
                        )
                        Log.e("report Api",result.data.code.toString())
                    }else{
                        _reportState.value= ReportState(
                            isSuccess = false,
                            isError = true,
                            error = result.data!!.message
                        )
                        Log.e("report Api",result.data.code.toString())

                    }
                }
                is Resource.Error -> {
                    _reportState.value = ReportState(
                        isError = true,
                        error = result.message ?: "An unexpected error occured"
                    )
                    Log.e("report Api", result.data?.code.toString())

                }

                is Resource.Loading->{
                    _reportState.value = ReportState(
                        isLoading = true
                    )
                }
            }
        }

    }

}