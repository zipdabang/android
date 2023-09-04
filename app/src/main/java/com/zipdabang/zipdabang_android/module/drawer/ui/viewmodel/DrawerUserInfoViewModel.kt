package com.zipdabang.zipdabang_android.module.drawer.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zipdabang.zipdabang_android.common.Constants
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.drawer.domain.usecase.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrawerUserInfoViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val dataStore: DataStore<Token>
) : ViewModel() {


    var stateUserInfo by mutableStateOf(UserInfoState())

    init{
        viewModelScope.launch {
            getUserInfo()
        }
    }
    private suspend fun getUserInfo(){
        val accessToken = dataStore.data.first().accessToken ?: Constants.TOKEN_NULL
        Log.e("drawer-userinfo-viewmodel","${accessToken}")

        getUserInfoUseCase(
            accessToken
        ).onEach { result->
            when(result){
                is Resource.Success -> {
                    stateUserInfo = stateUserInfo.copy(
                        email = result.data?.email ?: "",
                        profileUrl = result.data?.profileUrl ?: "",
                        name = result.data?.name ?: "",
                        birthday = result.data?.birth ?: "",
                        gender = result.data?.gender ?: "",
                        phoneNumber = result.data?.phoneNum ?: "",
                        nickname = result.data?.nickname ?: "",
                    )
                    Log.e("drawer-userinfo-viewmodel", "성공 ${result.data}")
                }
                is Resource.Error ->{
                    stateUserInfo = stateUserInfo.copy(error = result.message ?: "An unexpeted error occured")
                    Log.e("drawer-userinfo-viewmodel", "에러")
                }
                is Resource.Loading -> {
                    stateUserInfo = stateUserInfo.copy(isLoading = true)
                    Log.e("drawer-userinfo-viewmodel", "로딩중")
                }
            }
        }.launchIn(viewModelScope)
    }
}