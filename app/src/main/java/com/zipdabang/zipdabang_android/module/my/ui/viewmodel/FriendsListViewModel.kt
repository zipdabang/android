package com.zipdabang.zipdabang_android.module.my.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.zipdabang.zipdabang_android.core.DeviceSize
import com.zipdabang.zipdabang_android.module.detail.recipe.common.DeviceScreenSize
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FriendsListViewModel @Inject constructor(
    @DeviceSize private val deviceSize: DeviceScreenSize
) : ViewModel(){

    fun getDeviceSize(): DeviceScreenSize {
        return deviceSize
    }
}