package com.zipdabang.zipdabang_android.module.my.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import com.zipdabang.zipdabang_android.core.DeviceSize
import com.zipdabang.zipdabang_android.module.detail.recipe.common.DeviceScreenSize
import com.zipdabang.zipdabang_android.module.drawer.ui.quit.previewQuitScreen
import com.zipdabang.zipdabang_android.module.my.domain.repository.MyRepository
import com.zipdabang.zipdabang_android.module.my.domain.repository.PagingFollowRepository
import com.zipdabang.zipdabang_android.module.my.domain.repository.PagingFollowingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FriendsListViewModel @OptIn(ExperimentalPagingApi::class)
@Inject constructor(
    @DeviceSize private val deviceSize: DeviceScreenSize,
    followRepository: PagingFollowRepository,
    followingRepository: PagingFollowingRepository
) : ViewModel(){

    fun getDeviceSize(): DeviceScreenSize {
        return deviceSize
    }


    @OptIn(ExperimentalPagingApi::class)
    val getFollowItems = followRepository.getFollowItems().cachedIn(viewModelScope)

    val getFollowingItems = followingRepository.getFollowItems().cachedIn(viewModelScope)

}