package com.zipdabang.zipdabang_android.module.my.ui.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.module.my.domain.usecase.ClearBlockUseCase
import com.zipdabang.zipdabang_android.module.my.domain.usecase.GeOtherInfoUseCase
import com.zipdabang.zipdabang_android.module.my.domain.usecase.GeOtherRecipePreviewUseCase
import com.zipdabang.zipdabang_android.module.my.domain.usecase.PostFollowOrCancelUseCase
import com.zipdabang.zipdabang_android.module.my.ui.state.CancelBlock
import com.zipdabang.zipdabang_android.module.my.ui.state.CommonInfoState
import com.zipdabang.zipdabang_android.module.my.ui.state.FollowOrCancel
import com.zipdabang.zipdabang_android.module.my.ui.state.OtherInfo
import com.zipdabang.zipdabang_android.module.my.ui.state.OtherRecipePreviewState
import com.zipdabang.zipdabang_android.module.my.ui.state.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@SuppressLint("SuspiciousIndentation")
@HiltViewModel
class MyForOthersViewModel @Inject constructor(
    val getOtherInfoUseCase: GeOtherInfoUseCase,
    val getOtherRecipePreviewUseCase: GeOtherRecipePreviewUseCase,
    val followOrCancelUseCase: PostFollowOrCancelUseCase,
    val cancelBlockUseCase: ClearBlockUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _otherInfoState = mutableStateOf(OtherInfo())
    val otherInfoState = _otherInfoState

    private var _commonInfoState = mutableStateOf(CommonInfoState())
    val commonInfoState = _commonInfoState

    private var _profileState = mutableStateOf(ProfileState())
    val profileState = _profileState

    private var _otherRecipePreviewState = mutableStateOf(OtherRecipePreviewState())
    val otherRecipePreviewState = _otherRecipePreviewState

    private var _followOrCancelSuccessState = mutableStateOf(FollowOrCancel())
    val followOrCancelSuccessState = _followOrCancelSuccessState

    private var _cancelBlockState= mutableStateOf(CancelBlock())
    val cancelBlockState = _cancelBlockState

   private  val userId = savedStateHandle.get<Int>("userId")

    init {

        userId?.let {
            getOtherInfo()
            getOtherPreviewRecipe()
        }
        Log.e("OhterInfoViewModel", userId.toString())
    }


    fun getOtherInfo() {
        if (userId != null) {
            getOtherInfoUseCase(userId).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        Log.e("otherSuccess", result.code.toString())

                        _commonInfoState.value = CommonInfoState(
                            nickName = result.data!!.result.nickname,
                            followingNum = result.data.result.followingCount,
                            followNum = result.data.result.followerCount,
                            profileUrl = result.data.result.imageUrl,
                            isFollowing = result.data.result.checkFollowing,
                            isFollower = result.data.result.checkFollower,
                            isCheckSelf = result.data.result.checkSelf
                        )
                        _profileState.value = ProfileState(
                            caption = result.data.result.caption,
                            preferCategory = result.data.result.memberPreferCategoryDto.categories
                        )
                        _otherInfoState.value = OtherInfo(
                            isSuccess = true,
                            isLoading = false,
                            isError = false,
                            isBlock = false
                        )

                    }

                    is Resource.Loading -> {
                        _otherInfoState.value = OtherInfo(
                            isLoading = true,
                            isError = false,
                            isSuccess = false
                        )
                    }

                    is Resource.Error -> {
                        Log.e("otherError", result.code.toString())
                        if (result.code == 4066) _otherInfoState.value = OtherInfo(
                            isError = true,
                            isBlock = true,
                            isSuccess = false
                        )
                        else {
                            _otherInfoState.value = OtherInfo(
                                isError = true,
                                isBlock = false,
                                isSuccess = false
                            )
                        }
                        result.message?.let { Log.e("error in other page Api", it) }
                    }
                }


            }.launchIn(viewModelScope)
        }


    }

    fun getOtherPreviewRecipe() {
        if (userId != null) {
            getOtherRecipePreviewUseCase(userId).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        if (result.data?.result != null) {
                            _otherRecipePreviewState.value = OtherRecipePreviewState(
                                recipeList = result.data.result.recipeList,
                                isSuccess = true,
                                isLoading = false,
                                isError = false
                            )
                        }
                        //           Log.e("otherPreviewList", result.data.result.totalElements.toString())
                        else {
                            _otherRecipePreviewState.value = OtherRecipePreviewState(
                                recipeList = emptyList(),
                                isSuccess = true,
                                isLoading = false,
                                isError = false
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _otherRecipePreviewState.value = OtherRecipePreviewState(
                            isLoading = true,
                            isError = false,
                            isSuccess = false
                        )
                    }

                    is Resource.Error -> {
                        _otherRecipePreviewState.value = OtherRecipePreviewState(
                            isError = true,
                            isLoading = false,
                            isSuccess = false
                        )
                        result.message?.let { Log.e("error in other page Api", it) }
                    }
                }


            }.launchIn(viewModelScope)
        }
    }


    fun followOrCancel(targetId: Int, isToast: () -> Unit = {}) {
        followOrCancelUseCase(targetId).onEach { result ->
            when (result) {
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
                    Log.e(
                        "followOrcancel Api in Error",
                        "code :${result.code} message : ${result.message.toString()}"
                    )


                }

                is Resource.Loading -> {

                    _followOrCancelSuccessState.value = FollowOrCancel(
                        isError = false,
                        isLoading = true,
                    )

                }

            }

        }.launchIn(viewModelScope)

    }

    fun cancelBlock() {
        if (userId != null) {
            cancelBlockUseCase(userId).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        if (result.code == 2000) {
                            _cancelBlockState.value = CancelBlock(
                                isSuccess = true,
                                isLoading = false
                            )
                            getOtherInfo()
                            getOtherPreviewRecipe()
                        }

                    }

                    is Resource.Error -> {
                        _cancelBlockState.value = CancelBlock(
                            isError = true,
                            isLoading = false,
                            error = result.message.toString()
                        )
                        Log.e(
                            "cancelBlock Api in Error",
                            "code :${result.code} message : ${result.message.toString()}"
                        )


                    }

                    is Resource.Loading -> {
                        _cancelBlockState.value = CancelBlock(
                            isError = false,
                            isLoading = true,
                        )

                    }

                }

            }.launchIn(viewModelScope)
        }

    }

}