package com.zipdabang.zipdabang_android.module.sign_up.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class AuthSharedViewModel @Inject constructor(
    //savedStateHandle: SavedStateHandle
): ViewModel() {

    companion object {
        const val TAG = "AuthSharedViewModel"
    }

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _profile = MutableStateFlow("")
    val profile = _profile.asStateFlow()

    // 다른 회원정보들 dto로 만들어서 담기?

    fun updateEmail(email: String) {
        _email.value = email
    }

    fun updateProfile(profile: String) {
        _profile.value = profile
    }


    var state by mutableStateOf(RegistrationFormState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()


    fun onEvent(event : RegistrationFormEvent){
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
    }

    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "cleared")
    }
}