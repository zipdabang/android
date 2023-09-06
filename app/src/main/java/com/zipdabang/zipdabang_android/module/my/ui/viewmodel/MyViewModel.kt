package com.zipdabang.zipdabang_android.module.my.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.drawer.domain.usecase.GetUserInfoUseCase
import com.zipdabang.zipdabang_android.module.drawer.ui.viewmodel.userinfostate.UserInfoState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val dataStore: DataStore<Token>
) : ViewModel(){
    var stateUserInfo by mutableStateOf(UserInfoState())

}