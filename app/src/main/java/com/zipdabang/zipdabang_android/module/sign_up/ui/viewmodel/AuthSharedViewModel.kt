package com.zipdabang.zipdabang_android.module.sign_up.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.module.sign_up.domain.usecase.GetBeveragesUseCase
import com.zipdabang.zipdabang_android.module.sign_up.domain.usecase.GetTermsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AuthSharedViewModel @Inject constructor(
    private val getTermsUseCase: GetTermsUseCase,
    private val getBeveragesUseCase: GetBeveragesUseCase,
    //savedStateHandle: SavedStateHandle
): ViewModel() {

    companion object {
        const val TAG = "AuthSharedViewModel"
    }

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()
    private val _profile = MutableStateFlow("")
    val profile = _profile.asStateFlow()
    fun updateEmail(email: String) {
        _email.value = email
    }
    fun updateProfile(profile: String) {
        _profile.value = profile
    }


    //termsAgree - 전체동의하기
    private val _stateTermsAllagree = MutableStateFlow(true)
    val stateTermsAllagree = _stateTermsAllagree.asStateFlow()
    //termsAgree - 전체동의하기 이외
    private val _stateTermsListAgree = MutableStateFlow(listOf(true, true, true, true, true))
    val stateTermsListAgree = _stateTermsListAgree.asStateFlow()
    fun updateTermsAllagree(isChecked: Boolean){
        _stateTermsAllagree.value = isChecked
        _stateTermsListAgree.value = _stateTermsListAgree.value.map { _ -> isChecked }
        Log.e("termsAgree", "stateTermsAllagree: $isChecked")
        Log.e("termsAgree", "stateTermsListAgree: ${_stateTermsListAgree.value}")
    }
    fun updateTermsListAgree(id : Int, isChecked : Boolean) {
        _stateTermsListAgree.value = _stateTermsListAgree.value.mapIndexed{ index, value ->
            if(index == id) {
                isChecked
            } else {
                value
            }
        }
        Log.e("termsAgree", "stateTermsListAgree: ${_stateTermsListAgree.value}")
    }


    /*API*/
    //terms - GET api
    private val _stateTerms = mutableStateOf(TermsListState())
    val stateTerms : State<TermsListState> = _stateTerms
    //preferences - GET api
    private val _statePreferences = mutableStateOf(BeveragesListState())
    val statePreferences : State<BeveragesListState> = _statePreferences
    init{
        getTerms()
        getBeverages()
    }
    private fun getTerms(){
        getTermsUseCase().onEach {result ->
            Log.e("signup-terms","${result.code}")
            when(result) {
                is Resource.Success ->{
                    _stateTerms.value = TermsListState(
                        termsList = result.data?.termsList ?: emptyList(),
                        size = result.data?.size ?: 0,
                    )
                }
                is Resource.Error ->{
                    _stateTerms.value = TermsListState(error = result.message ?:"An unexpeted error occured")
                }
                is Resource.Loading ->{
                    _stateTerms.value = TermsListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
    private fun getBeverages(){
        getBeveragesUseCase().onEach {result ->
            Log.e("signup-preferences","${result.code}")
            when(result){
                is Resource.Success ->{
                    _statePreferences.value = BeveragesListState(
                        beverageList = result.data?.beverageCategoryList ?: emptyList(),
                        size = result.data?.size  ?: 0
                    )
                }
                is Resource.Error ->{
                    _statePreferences.value = BeveragesListState(error = result.message ?:"An unexpeted error occured")
                }
                is Resource.Loading ->{
                    _statePreferences.value = BeveragesListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }


    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "cleared")
    }
}