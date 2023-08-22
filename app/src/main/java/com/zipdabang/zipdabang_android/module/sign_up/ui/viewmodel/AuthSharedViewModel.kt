package com.zipdabang.zipdabang_android.module.sign_up.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.BeverageCategory
import com.zipdabang.zipdabang_android.module.sign_up.domain.usecase.GetBeveragesUseCase
import com.zipdabang.zipdabang_android.module.sign_up.domain.usecase.GetNicknameUseCase
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
    private val getNicknameUseCase: GetNicknameUseCase,
    //savedStateHandle: SavedStateHandle
): ViewModel() {

    companion object {
        const val TAG = "AuthSharedViewModel"
    }

    /*LoginScreen*/
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



    /*TermsScreen*/
    //termsAgree - 전체동의하기
    private val _stateTermsAllagree = MutableStateFlow(true)
    val stateTermsAllagree = _stateTermsAllagree.asStateFlow()
    //termsAgree - 전체동의하기 이외
    private val _stateTermsListAgree = MutableStateFlow(listOf(true, true, true, true, true))
    val stateTermsListAgree = _stateTermsListAgree.asStateFlow()
    //terms - validate
    private val _stateTermsValidate = MutableStateFlow(true)
    val stateTermsValidate = _stateTermsValidate.asStateFlow()
    fun updateTermsAllagree(isChecked: Boolean){
        _stateTermsAllagree.value = isChecked
        if (isChecked) {
            _stateTermsListAgree.value = List(_stateTermsListAgree.value.size) { true }
        }
        validateTerms()
        //Log.e("termsAgree-viewmodel", "stateTermsAllagree: ${stateTermsAllagree.value}")
        //Log.e("termsAgree-viewmodel", "stateTermsListAgree: ${stateTermsListAgree.value}")
    }
    fun updateTermsListAgree(id : Int, isChecked : Boolean) {
        _stateTermsListAgree.value = _stateTermsListAgree.value.toMutableList().apply {
            set(id, isChecked)
        }
        val allTermsAgreed = _stateTermsListAgree.value.all { it } // 모든 요소가 true인지 검사
        _stateTermsAllagree.value = allTermsAgreed // stateTermsAllagree 업데이트
        validateTerms()
        //Log.e("termsAgree-viewmodel", "stateTermsAllagree: ${stateTermsAllagree.value}")
        //Log.e("termsAgree-viewmodel", "stateTermsListAgree: ${stateTermsListAgree.value}")
    }
    fun updateTermsValidation(isValid: Boolean) {
        _stateTermsValidate.value = isValid
    }
    fun validateTerms(): Boolean {
        val termsInRange = stateTermsListAgree.value.subList(0, 4) // 0부터 3까지의 요소들
        val requiredTermsAgree = termsInRange.all { it } // 모든 요소가 true인지 검사
        updateTermsValidation(requiredTermsAgree) // _stateTermsValidate 값 업데이트
        return requiredTermsAgree // 모든 요소가 true인지 검사
    }


    /*UserInfoScreen*/
    //userinfo - name
    private val _stateNameValue = MutableStateFlow("")
    val stateNameValue = _stateNameValue.asStateFlow()
    //userinfo - birthday
    private val _stateBirthdayValue = MutableStateFlow("")
    val stateBirthdayValue = _stateBirthdayValue.asStateFlow()
    //userinfo - gender
    private val _stateGenderList = MutableStateFlow(listOf("남", "여"))
    val stateGenderList = _stateGenderList.asStateFlow()
    private val _stateGenderValue = MutableStateFlow(_stateGenderList.value[0])
    val stateGenderValue = _stateGenderValue.asStateFlow()
    //userinfo - Phonenumber
    private val _statePhonenumberValue = MutableStateFlow("")
    val statePhonenumberValue = _statePhonenumberValue.asStateFlow()
    //userinfo - Certificatenumber
    private val _stateCertificatenumberValue = MutableStateFlow("")
    val stateCertificatenumberValue = _stateCertificatenumberValue.asStateFlow()
    //userinfo - Zipcode
    private val _stateZipcodeValue = MutableStateFlow("")
    val stateZipcodeValue = _stateZipcodeValue.asStateFlow()
    //userinfo - Address
    private val _stateAddressValue = MutableStateFlow("")
    val stateAddressValue = _stateAddressValue.asStateFlow()
    //userinfo - Detailaddress
    private val _stateDetailaddressValue = MutableStateFlow("")
    val stateDetailaddressValue = _stateDetailaddressValue.asStateFlow()
    fun updateName(name : String){
        _stateNameValue.value = name
        Log.e("nickname-viewmodel","${stateNicknameValue.value}")
    }
    fun updateBirthday(birthday : String){
        _stateBirthdayValue.value = birthday
    }
    fun updatePhonenumber(phonenumber : String){
        _statePhonenumberValue.value = phonenumber
    }
    fun updateGender(gender : String){
        _stateGenderValue.value = gender
        Log.e("userinfo-viewmodel","${stateGenderValue.value}")
    }
    fun updateCertificatenumber(certificatenumber : String){
        _stateCertificatenumberValue.value = certificatenumber
    }
    fun updateZipcode(zipcode : String){
        _stateZipcodeValue.value = zipcode
    }
    fun updateAddress(address : String){
        _stateAddressValue.value = address
    }
    fun updateDetailaddres(detailaddress : String){
        _stateDetailaddressValue.value = detailaddress
    }


    /*NickNameScreen*/
    //nickname - text value
    private val _stateNicknameValue = MutableStateFlow("")
    val stateNicknameValue = _stateNicknameValue.asStateFlow()
    //nickname - api 시도한 횟수(tryCount)
    private val _stateTrycount = MutableStateFlow(0)
    val stateTrycount = _stateTrycount.asStateFlow()
    //nickname - api 시도하고 error상태인지
    private val _stateIsError = MutableStateFlow(false)
    val stateIsError = _stateIsError.asStateFlow()
    //nickname - errormessage
    private val _stateErrorMessage = MutableStateFlow("")
    val stateErrorMessage = _stateErrorMessage.asStateFlow()
    //nickname - api 시도하고 correct상태인지
    private val _stateIsCorrect = MutableStateFlow(false)
    val stateIsCorrect = _stateIsCorrect.asStateFlow()
    //nickname - correctmessage
    private val _stateCorrectMessage = MutableStateFlow("")
    val stateCorrectMessage = _stateCorrectMessage.asStateFlow()
    //nickname - validate
    private val _stateNicknameValidate = MutableStateFlow(false)
    val stateNicknameValidate = _stateNicknameValidate.asStateFlow()
    fun updateNickname(nickname : String){
        _stateNicknameValue.value = nickname
        if(_stateIsCorrect.value == true || _stateIsError.value == true){
            _stateIsError.value = false
            _stateIsCorrect.value = false
            _stateErrorMessage.value = ""
            _stateCorrectMessage.value = ""
        }
        Log.e("nickname-viewmodel","${stateNicknameValue.value}")
    }
    fun updateTrycount(){
        _stateTrycount.value ++
        getNickname()
    }
    fun updateIsError() : Boolean{
        if(_stateErrorMessage.value != ""){
            _stateIsError.value = true
            _stateIsCorrect.value = false
        }
        return _stateIsError.value
    }
    fun updateIsCorrect() : Boolean{
        if(_stateCorrectMessage.value != ""){
            _stateIsCorrect.value = true
            _stateIsError.value = false
        }
        return _stateIsCorrect.value
    }
    fun validateNickname() : Boolean{
        return false
    }



    /*PreferencesScreen*/
    //beverageList - 음료종류
    private val _stateBeverageList = MutableStateFlow(listOf(false, false, false, false, false, false, false, false))
    val stateBeverageList = _stateBeverageList.asStateFlow()
    //preferences - validate
    private val _statePreferencesValidate = MutableStateFlow(false)
    val statePreferencesValidate = _statePreferencesValidate.asStateFlow()
    fun updateBeverageList(id : Int, isChecked : Boolean) {
       _stateBeverageList.value = _stateBeverageList.value.toMutableList().apply{
           set(id, isChecked)
       }
        validatePreferences()
        //Log.e("preferences-viewmodel", "${stateBeverageList.value}")
    }
    fun updatePreferencesValidation(isValid: Boolean) {
        _statePreferencesValidate.value = isValid
    }
    fun validatePreferences() : Boolean {
        val atleastOneChecked = stateBeverageList.value.any { it }
        updatePreferencesValidation(atleastOneChecked)
        return atleastOneChecked
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
            Log.e("signup-terms","${result.data?.termsList}")
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
            Log.e("signup-preferences","${result.data?.beverageCategoryList}")
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
    private fun getNickname() {
        getNicknameUseCase(_stateNicknameValue.value).onEach{ result ->
            when(result){
                is Resource.Success ->{
                    if(result.data?.code ?: 0 == 2011){ //닉네임 가능
                        _stateCorrectMessage.value = result.data?.message ?: ""
                        _stateIsCorrect.value = true
                        Log.e("nickname-viewmodel", "${result.data?.message}")
                    } else if(result.data?.code ?: 0 == 2010){ //닉네임 중복
                        _stateErrorMessage.value = result.data?.message ?: ""
                        _stateIsError.value = true
                        Log.e("nickname-viewmodel", "${result.data?.message}")
                    } else { //4019 : 형식 //4020 : 욕
                        //엥 뭐지
                    }
                    updateIsError()
                    updateIsCorrect()
                    Log.e("nickname-viewmodel", "errormessage : ${_stateErrorMessage.value}")
                    Log.e("nickname-viewmodel", "correctmessage : ${_stateCorrectMessage.value}")
                }
                is Resource.Error ->{
                    Log.e("nickname-viewmodel", "에러")
                }
                is Resource.Loading->{
                    Log.e("nickname-viewmodel", "로딩중")
                }
            }
        }.launchIn(viewModelScope)
    }





    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "cleared")
    }
}