package com.zipdabang.zipdabang_android.module.my.ui.viewmodel

import android.util.Log
import android.view.View
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.module.my.domain.usecase.GeOtherInfoUseCase
import com.zipdabang.zipdabang_android.module.my.domain.usecase.GeOtherRecipePreviewUseCase
import com.zipdabang.zipdabang_android.module.my.ui.state.CommonInfoState
import com.zipdabang.zipdabang_android.module.my.ui.state.OtherInfo
import com.zipdabang.zipdabang_android.module.my.ui.state.OtherRecipePreviewState
import com.zipdabang.zipdabang_android.module.my.ui.state.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MyForOthersViewModel @Inject constructor(
    val getOtherInfoUseCase: GeOtherInfoUseCase,
    val getOtherRecipePreviewUseCase: GeOtherRecipePreviewUseCase,
     savedStateHandle: SavedStateHandle
) : ViewModel(){

    private var _otherInfoState = mutableStateOf(OtherInfo())
    val otherInfoState = _otherInfoState

    private var _commonInfoState = mutableStateOf(CommonInfoState())
    val commonInfoState = _commonInfoState

    private var _profileState= mutableStateOf(ProfileState())
    val profileState = _profileState

    private var _otherRecipePreviewState = mutableStateOf(OtherRecipePreviewState())
    val otherRecipePreviewState = _otherRecipePreviewState

     init {
         val id = savedStateHandle.get<Int>("userId")

         if (id != null) {
             getOtherInfo(id)
             getOtherPreviewRecipe(id)
         }

         Log.e("OhterInfoViewModel",id.toString())
     }


    fun getOtherInfo(targetId: Int){
        getOtherInfoUseCase(targetId).onEach {
            result ->
            when(result) {
                is Resource.Success ->{
                    _commonInfoState.value =  CommonInfoState(
                        nickName = result.data!!.result.nickname,
                        followingNum = result.data.result.followingCount,
                        followNum = result.data.result.followerCount,
                        profileUrl = result.data.result.imageUrl,
                        isFollowing = result.data.result.checkFollowing
                    )
                    _profileState.value= ProfileState(
                        caption = result.data.result.caption,
                        preferCategory = result.data.result.memberPreferCategoryDto.categories
                    )
                    _otherInfoState.value = OtherInfo(
                        isSuccess = true,
                        isLoading = false
                    )

                }

                is Resource.Loading ->{
                    _otherInfoState.value = OtherInfo(
                        isLoading = true
                    )
                }

                is Resource.Error -> {
                    _otherInfoState.value = OtherInfo(
                        isError = true
                    )
                    result.message?.let { Log.e("error in other page Api", it) }
                }
            }


        }.launchIn(viewModelScope)


    }

    fun getOtherPreviewRecipe(memberId : Int){
        getOtherRecipePreviewUseCase(memberId).onEach {
            result ->
            when(result) {
            is Resource.Success ->{
                _otherRecipePreviewState.value = OtherRecipePreviewState(
                    recipeList = result.data?.result!!.recipeList
                )
                Log.e("otherPreviewList", result.data.result.totalElements.toString())
        }

            is Resource.Loading ->{

        }

            is Resource.Error -> {
            _otherInfoState.value = OtherInfo(
                isError = true
            )
            result.message?.let { Log.e("error in other page Api", it) }
        }
        }




        }.launchIn(viewModelScope)
    }

}