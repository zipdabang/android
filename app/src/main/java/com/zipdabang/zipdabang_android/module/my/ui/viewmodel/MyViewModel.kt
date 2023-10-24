package com.zipdabang.zipdabang_android.module.my.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zipdabang.zipdabang_android.common.Constants
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.core.DeviceSize
import com.zipdabang.zipdabang_android.core.data_store.proto.CurrentPlatform
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.detail.recipe.common.DeviceScreenSize
import com.zipdabang.zipdabang_android.module.my.data.remote.myinfo.MemberPreferCategoryDto
import com.zipdabang.zipdabang_android.module.my.data.remote.myrecipes.preview.CompleteRecipesWithImgPreviewRecipe
import com.zipdabang.zipdabang_android.module.my.domain.usecase.GetCompleteRecipesPreviewUseCase
import com.zipdabang.zipdabang_android.module.my.domain.usecase.GetMyInfoUseCase
import com.zipdabang.zipdabang_android.module.my.ui.state.signout.SignOutState
import com.zipdabang.zipdabang_android.module.my.ui.state.my.MyUserInfoState
import com.zipdabang.zipdabang_android.module.my.ui.state.myrecipe.preview.CompleteRecipePreview
import com.zipdabang.zipdabang_android.module.my.use_case.SignOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.concurrent.CancellationException
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val dataStore: DataStore<Token>,
    private val signOutUseCase: SignOutUseCase,
    private val getMyInfoUseCase: GetMyInfoUseCase,
    private val getCompleteRecipesPreviewUseCase: GetCompleteRecipesPreviewUseCase
) : ViewModel(){

    var stateMyUserInfo by mutableStateOf(
        MyUserInfoState(
            memberPreferCategoryDto =
                MemberPreferCategoryDto(
                    categories = listOf(),
                    size = 0
                )
            )
        )
    var stateCompleteRecipesPreview by mutableStateOf(
        CompleteRecipePreview(
            isLoading = false,
            recipeList = emptyList(),
            totalElements = 0,
            error = ""
        )
    )

    init{
        viewModelScope.launch {
            getMyInfo()
            getCompleteRecipesPreview()
        }
    }
    suspend fun getMyInfo(){
        val accessToken = "Bearer " + dataStore.data.first().accessToken ?: Constants.TOKEN_NULL
        getMyInfoUseCase(
            accessToken
        ).onEach {response ->
            when(response){
                is Resource.Success ->{
                    stateMyUserInfo = stateMyUserInfo.copy(
                        isLoading = false,
                        oneline = response.data?.result?.caption ?: "",
                        nickname = response.data?.result?.nickname ?: "",
                        profileUrl = response.data?.result?.imageUrl ?: "",
                        followerCount = response.data?.result?.followerCount ?: 0,
                        followingCount = response.data?.result?.followingCount ?: 0,
                        memberPreferCategoryDto = response.data?.result?.memberPreferCategoryDto ?: MemberPreferCategoryDto(categories = listOf(), size = 0),
                    )
                    Log.e("my-info","${response.data} ${stateMyUserInfo.oneline}")
                }
                is Resource.Error ->{
                    stateMyUserInfo = stateMyUserInfo.copy(error = response.message ?: "An unexpeted error occured")
                }
                is Resource.Loading->{
                    stateMyUserInfo = stateMyUserInfo.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    suspend fun getCompleteRecipesPreview(){
        var accessToken = "Bearer " + dataStore.data.first().accessToken.toString()

        try{
            val result = getCompleteRecipesPreviewUseCase(accessToken)

            result.collect{result->
                when(result){
                    is Resource.Success->{
                        stateCompleteRecipesPreview = stateCompleteRecipesPreview.copy(
                            isLoading = false,
                            totalElements = result.data?.totalElements ?: 0,
                            recipeList = result.data?.recipeList?.mapIndexed { index, items ->
                                CompleteRecipesWithImgPreviewRecipe(
                                    categoryId = items.categoryId,
                                    comments= items.comments,
                                    createdAt= items.createdAt,
                                    isLiked= items.isLiked,
                                    isScrapped= items.isScrapped,
                                    likes= items.likes,
                                    nickname= items.nickname,
                                    recipeId= items.recipeId,
                                    recipeName= items.recipeName,
                                    scraps= items.scraps,
                                    thumbnailUrl= items.thumbnailUrl,
                                    updatedAt= items.updatedAt,
                                )
                            } ?: emptyList()
                        )
                        Log.e("my_completerecipe_preview", "성공 : ${result} ${result.message} ${result.data} ${result.code}")
                    }
                    is Resource.Error ->{
                        Log.e("my_completerecipe_preview", "에러 : ${result} ${result.message} ${result.data} ${result.code}")
                        stateCompleteRecipesPreview = stateCompleteRecipesPreview.copy(
                            error = result.message ?: "An unexpeted error occured"
                        )
                    }
                    is Resource.Loading ->{
                        stateCompleteRecipesPreview = stateCompleteRecipesPreview.copy(isLoading = true)
                        Log.e("my_completerecipe_preview",  "로딩중 : ${result} ${result.message} ${result.data} ${result.code}")
                    }
                }
            }
        }  catch (e: Exception) {}
    }



    private val _signOutState = MutableStateFlow(SignOutState())
    val signOutState: StateFlow<SignOutState> = _signOutState

    fun signOut(
        onSignOutSuccessful: () -> Unit
    ) {
        signOutUseCase().onEach { resource ->
            Log.d("logout viewmodel", "usecase called")
            when (resource) {
                is Resource.Loading -> {
                    _signOutState.value = SignOutState(
                        isLoading = true
                    )
                }

                is Resource.Success -> {
                    Log.d("Logout", resource.data.toString())
                    _signOutState.value = SignOutState(
                        isLoading = false,
                        isSuccessful = true
                    )

                    dataStore.updateData {
                        it.copy(
                            accessToken = null,
                            refreshToken = null,
                            platformToken = null,
                            platformStatus = CurrentPlatform.NONE
                        )
                    }

                    onSignOutSuccessful()
                }


                is Resource.Error -> {
                    Log.d("Logout", "error ${resource.code} : ${resource.message}")
                    _signOutState.value = SignOutState(
                        isLoading = false,
                        errorMessage = resource.message,
                        isSuccessful = false
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}