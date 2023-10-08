package com.zipdabang.zipdabang_android.module.my.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.core.DeviceSize
import com.zipdabang.zipdabang_android.module.detail.recipe.common.DeviceScreenSize
import com.zipdabang.zipdabang_android.module.drawer.ui.quit.previewQuitScreen
import com.zipdabang.zipdabang_android.module.my.domain.repository.MyRepository
import com.zipdabang.zipdabang_android.module.my.domain.repository.PagingFollowRepository
import com.zipdabang.zipdabang_android.module.my.domain.repository.PagingFollowingRepository
import com.zipdabang.zipdabang_android.module.my.domain.usecase.PostFollowOrCancelUseCase
import com.zipdabang.zipdabang_android.module.my.ui.state.FollowOrCancel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FriendsListViewModel @OptIn(ExperimentalPagingApi::class)
@Inject constructor(
    @DeviceSize private val deviceSize: DeviceScreenSize,
    val followRepository: PagingFollowRepository,
    val followingRepository: PagingFollowingRepository,
    val followOrCancelUseCase: PostFollowOrCancelUseCase
) : ViewModel() {

    private var _followOrCancelSuccessState = mutableStateOf(FollowOrCancel())
    val followOrCancelSuccessState = _followOrCancelSuccessState
    fun getDeviceSize(): DeviceScreenSize {
        return deviceSize
    }


    @OptIn(ExperimentalPagingApi::class)
    var getFollowItems = followRepository.getFollowItems().cachedIn(viewModelScope)
    var getFollowingItems = followingRepository.getFollowingItems().cachedIn(viewModelScope)
    @OptIn(ExperimentalPagingApi::class)
    fun refresh(){
        getFollowItems = followRepository.getFollowItems().cachedIn(viewModelScope)
        getFollowingItems = followingRepository.getFollowingItems().cachedIn(viewModelScope)
    }
    fun followOrCancel(targetId : Int, isToast : () -> Unit){
        followOrCancelUseCase(targetId).onEach {
            result ->
            when(result) {
                is Resource.Success -> {
                    if (result.data!!.isSuccess) {
                        _followOrCancelSuccessState.value = FollowOrCancel(
                            isSuccess = true,
                            isLoading = false,
                        )
                        if (result.data.result.isFollowing) {
                            _followOrCancelSuccessState.value = FollowOrCancel(
                                isFollowing = true
                            )
                        } else {
                            _followOrCancelSuccessState.value = FollowOrCancel(
                                isFollowing = false
                            )
                        }
                        isToast()

                        Log.e("followorcancel api", result.data.code.toString())
                    }

                }

                is Resource.Error -> {
                    _followOrCancelSuccessState.value = FollowOrCancel(
                        isError = true,
                        isLoading = false,
                        error = result.message.toString()
                    )
                    Log.e("followOrcancel Api in Error","code :${result.code} message : ${result.message.toString()}")


                }
                is Resource.Loading -> {

                    _followOrCancelSuccessState.value = FollowOrCancel(
                        isError = false,
                        isLoading = true ,
                    )

                }

            }





        }.launchIn(viewModelScope)
    }
}