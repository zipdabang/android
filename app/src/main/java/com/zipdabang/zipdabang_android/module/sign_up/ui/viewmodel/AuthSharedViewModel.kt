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


    /*var state by mutableStateOf(RegistrationFormState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()*/




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


    /*fun onEvent(event : RegistrationFormEvent){
        when(event){
            is RegistrationFormEvent.TermsChanged ->{
                state = state.copy(terms = event.checked)
            }
            is RegistrationFormEvent.NameChanged -> {
                state = state.copy(name = event.name)
            }
            is RegistrationFormEvent.BirthdayChanged ->{
                state = state.copy(birthday = event.birthday)
            }
            is RegistrationFormEvent.GenderChanged ->{
                state = state.copy(gender = event.gender)
            }
            is RegistrationFormEvent.PhonenumberChanged ->{
                state = state.copy(phoneNumber = event.phonenumber)
            }
            is RegistrationFormEvent.ZipcodeChanged ->{
                state = state.copy(zipCode = event.zipcode)
            }
            is RegistrationFormEvent.AddressChanged ->{
                state = state.copy(address = event.address)
            }
            is RegistrationFormEvent.DetailaddressChanged ->{
                state = state.copy(detailAddress = event.detailaddress)
            }
            is RegistrationFormEvent.NicknameChanged ->{
                state = state.copy(nickname = event.nickname)
            }

            is RegistrationFormEvent.SubmitTerms ->{

            }
            is RegistrationFormEvent.SubmitUserInfo ->{

            }
            is RegistrationFormEvent.SubmitPreferences ->{

            }
        }
    }


    private fun submitUserInfo(){

    }

    private fun submitNickname(){

    }


    sealed class ValidationEvent {
        object Success : ValidationEvent()
    }*/

    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "cleared")
    }
}