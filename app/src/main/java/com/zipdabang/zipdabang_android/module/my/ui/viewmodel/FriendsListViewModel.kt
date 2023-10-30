package com.zipdabang.zipdabang_android.module.my.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.compose.collectAsLazyPagingItems
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.core.DeviceSize
import com.zipdabang.zipdabang_android.module.detail.recipe.common.DeviceScreenSize
import com.zipdabang.zipdabang_android.module.drawer.ui.quit.previewQuitScreen
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.follow.Following
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.follow.search.FollowInfoDB
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.following.Follower
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.following.search.FollowerInfoDB
import com.zipdabang.zipdabang_android.module.my.domain.repository.MyRepository
import com.zipdabang.zipdabang_android.module.my.domain.repository.PagingFollowRepository
import com.zipdabang.zipdabang_android.module.my.domain.repository.PagingFollowingRepository
import com.zipdabang.zipdabang_android.module.my.domain.repository.PagingSearchFollowerRepository
import com.zipdabang.zipdabang_android.module.my.domain.repository.PagingSearchFollowingRepository
import com.zipdabang.zipdabang_android.module.my.domain.usecase.PostFollowOrCancelUseCase
import com.zipdabang.zipdabang_android.module.my.domain.usecase.UserReportUseCase
import com.zipdabang.zipdabang_android.module.my.ui.state.FollowOrCancel
import com.zipdabang.zipdabang_android.module.my.ui.state.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendsListViewModel @OptIn(ExperimentalPagingApi::class)
@Inject constructor(
    @DeviceSize private val deviceSize: DeviceScreenSize,
    val followRepository: PagingFollowRepository,
    val searchFollowRepository : PagingSearchFollowingRepository,
    val followingRepository: PagingFollowingRepository,
    val searchFollowerRepository: PagingSearchFollowerRepository,
    val followOrCancelUseCase: PostFollowOrCancelUseCase,
    val userReportUseCase: UserReportUseCase
) : ViewModel() {

    private var _followOrCancelSuccessState = mutableStateOf(FollowOrCancel())
    val followOrCancelSuccessState = _followOrCancelSuccessState
    fun getDeviceSize(): DeviceScreenSize {
        return deviceSize
    }

    private val _searchFollowings = MutableStateFlow<PagingData<FollowInfoDB>>(PagingData.empty())
    val searchFollowings = _searchFollowings

    private val _searchFollowers = MutableStateFlow<PagingData<FollowerInfoDB>>(PagingData.empty())
    val searchFollowers= _searchFollowers


    private val _followItems = MutableStateFlow<PagingData<Following>>(PagingData.empty())
    val followItems = _followItems


    private val _followerItems = MutableStateFlow<PagingData<Follower>>(PagingData.empty())
    val followerItems = _followerItems

    @OptIn(ExperimentalPagingApi::class)
    var getFollowItems = followRepository.getFollowItems().cachedIn(viewModelScope)
    var getFollowingItems = followingRepository.getFollowingItems().cachedIn(viewModelScope)

    @OptIn(ExperimentalPagingApi::class)
    fun getFollowItems(){
        viewModelScope.launch {

            followRepository.getFollowItems().cachedIn(viewModelScope).collect{
                _followItems.value = it
            }
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    fun getFollowerItems(){
        viewModelScope.launch {

            followingRepository.getFollowingItems().cachedIn(viewModelScope).collect{
                _followerItems.value = it
            }
        }
    }

//    @OptIn(ExperimentalPagingApi::class)
//    fun refresh(){
//        getFollowItems = followRepository.getFollowItems().cachedIn(viewModelScope)
//        getFollowingItems = followingRepository.getFollowingItems().cachedIn(viewModelScope)
//    }

    fun followOrCancel(targetId : Int, isToast : () -> Unit, isRefresh : ()-> Unit){
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
                        isRefresh()

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


    fun searchFollow(searchText: String)  {
        Log.e("viewmodel in search","items")
        viewModelScope.launch {

            searchFollowRepository.getFollowingItems(searchText).cachedIn(viewModelScope).collect{
                _searchFollowings.value = it
            }

        }

        }

    fun searchFollower(searchText: String)  {
        Log.e("viewmodel in search","items")
        viewModelScope.launch {

            searchFollowerRepository.getFollowerItems(searchText).cachedIn(viewModelScope).collect{
                _searchFollowers.value = it
            }


        }

    }

    fun userReport(targetId : Int, isToast : () -> Unit, isOwner : () -> Unit){
        userReportUseCase(targetId).onEach {
                result ->
            when(result) {
                is Resource.Success -> {
                    if (result.data!!.isSuccess) {
                        isToast()

                        Log.e("userReport api", result.data.code.toString())
                    }

                }

                is Resource.Error -> {
                    if(result.code==4075) {
                        isOwner()
                    }
                    Log.e("userReport Api in Error","code :${result.code} message : ${result.message.toString()}")


                }
                is Resource.Loading -> {
                    Log.e("userReport api", "Loading")

                }

            }





        }.launchIn(viewModelScope)
    }




}