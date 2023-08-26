package com.zipdabang.zipdabang_android.module.sign_up.ui.viewmodel

import android.provider.ContactsContract.CommonDataKinds.Nickname
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.BeverageCategory
import com.zipdabang.zipdabang_android.module.sign_up.domain.usecase.GetBeveragesUseCase
import com.zipdabang.zipdabang_android.module.sign_up.domain.usecase.GetNicknameUseCase
import com.zipdabang.zipdabang_android.module.sign_up.domain.usecase.GetPhoneSmsUseCase
import com.zipdabang.zipdabang_android.module.sign_up.domain.usecase.GetTermsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthSharedViewModel @Inject constructor(
    private val getTermsUseCase: GetTermsUseCase,
    private val getBeveragesUseCase: GetBeveragesUseCase,
    private val getNicknameUseCase: GetNicknameUseCase,
    private val getPhoneSmsUseCase: GetPhoneSmsUseCase,
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
    var stateTermsForm by mutableStateOf(TermsFormState())
    fun onTermsEvent(event : TermsFormEvent){
        when(event){
            is TermsFormEvent.AllAgreeChanged -> {
                stateTermsForm = stateTermsForm.copy(allAgree = !stateTermsForm.allAgree)
                Log.e("termsAgree-viewmodel", "stateTermsAllagree: ${stateTermsForm}")
                //updateValidation()
                clickAllAgree()
            }
            is TermsFormEvent.RequiredOneChanged -> {
                stateTermsForm = stateTermsForm.copy(requiredOne = !stateTermsForm.requiredOne)
                Log.e("termsAgree-viewmodel", "stateTermsAllagree: ${stateTermsForm}")
                //updateValidation()
                updateAllagree()
            }
            is TermsFormEvent.RequiredTwoChanged -> {
                stateTermsForm = stateTermsForm.copy(requiredTwo = !stateTermsForm.requiredTwo)
                Log.e("termsAgree-viewmodel", "stateTermsAllagree: ${stateTermsForm}")
               // updateValidation()
                updateAllagree()
            }
            is TermsFormEvent.RequiredThreeChanged ->{
                stateTermsForm = stateTermsForm.copy(requiredThree = !stateTermsForm.requiredThree)
                Log.e("termsAgree-viewmodel", "stateTermsAllagree: ${stateTermsForm}")
                //updateValidation()
                updateAllagree()
            }
            is TermsFormEvent.RequiredFourChanged -> {
                stateTermsForm = stateTermsForm.copy(requiredFour = !stateTermsForm.requiredFour)
                Log.e("termsAgree-viewmodel", "stateTermsAllagree: ${stateTermsForm}")
                //updateValidation()
                updateAllagree()
            }
            is TermsFormEvent.ChoiceChanged -> {
                stateTermsForm = stateTermsForm.copy(choice = !stateTermsForm.choice)
                Log.e("termsAgree-viewmodel", "stateTermsAllagree: ${stateTermsForm}")
                //updateValidation()
                updateAllagree()
            }
            is TermsFormEvent.BtnChanged -> {
                updateValidation()
                Log.e("termsAgree-viewmodel", "stateTermsAllagree: ${stateTermsForm}")
            }
            else -> {}
        }
    }
    //TermsEvent 관리
    private fun updateValidation() {
        val isAllRequiredTrue = listOf(
            stateTermsForm.requiredOne,
            stateTermsForm.requiredTwo,
            stateTermsForm.requiredThree,
            stateTermsForm.requiredFour,
        ).all{ it }

        if(isAllRequiredTrue){
            stateTermsForm = stateTermsForm.copy(btnEnabled = true)
        } else{
            stateTermsForm = stateTermsForm.copy(btnEnabled = false)
        }
    }
    //넘어가기 버튼 변화
    private fun clickAllAgree(){
        if(stateTermsForm.allAgree){
            stateTermsForm = stateTermsForm.copy(
                requiredOne = true,
                requiredTwo = true,
                requiredThree = true,
                requiredFour = true,
                choice = true,
                btnEnabled = true
            )
            return
        } else{
            stateTermsForm = stateTermsForm.copy(
                requiredOne = false,
                requiredTwo = false,
                requiredThree = false,
                requiredFour = false,
                choice = false,
                btnEnabled = false
            )
            return
        }
    }
    //전체 동의하기 버튼 클릭했을때, 전체 동의하기 버튼 변화
    private fun updateAllagree(){
        val hasTermFalse = listOf(
            stateTermsForm.requiredOne,
            stateTermsForm.requiredTwo,
            stateTermsForm.requiredThree,
            stateTermsForm.requiredFour,
            stateTermsForm.choice,
        ).any{ !it }

        if(hasTermFalse){
            stateTermsForm = stateTermsForm.copy(allAgree = false)
        } else{
            stateTermsForm = stateTermsForm.copy(allAgree = true)
        }
    }
    //전체 동의하기 이외의 버튼을 클릭했을때, 전체 동의하기 버튼 변화




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
    var stateNicknameForm by mutableStateOf(NicknameFormState())
    fun onNicknameEvent(event : NicknameFormEvent) {
        when(event){
            is NicknameFormEvent.NicknameChanged ->{
                stateNicknameForm = stateNicknameForm.copy(nickname = event.nickname)
            }
        }
    }
    fun btnNicknameClicked(){
        getNickname()
    }
    //api 호출

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
    fun updateNicknameValidation(isValid : Boolean){
        _stateNicknameValidate.value = isValid
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
//    private val _stateTerms = mutableStateOf(TermsListState())
//    val stateTerms : State<TermsListState> = _stateTerms
    //preferences - GET api
    private val _statePreferences = mutableStateOf(BeveragesListState())
    val statePreferences : State<BeveragesListState> = _statePreferences
    init{
        getTerms()
        getBeverages()
    }
    private fun getTerms(){
        getTermsUseCase().onEach {result ->
            when(result) {
                is Resource.Success ->{
                    /*_stateTerms.value = TermsListState(
                        termsList = result.data?.termsList ?: emptyList(),
                        size = result.data?.size ?: 0,
                    )*/
                    stateTermsForm = TermsFormState(
                        termsList = result.data?.termsList ?: emptyList(),
                        size = result.data?.size ?: 0,
                        requiredOneTitle = result.data?.termsList?.get(0)?.termsTitle ?: "",
                        requiredOneBody = result.data?.termsList?.get(0)?.termsBody ?: "",
                        isMoreToSeeRequiredOne = result.data?.termsList?.get(0)?.isMoreToSee ?: false,
                        requiredTwoTitle = result.data?.termsList?.get(1)?.termsTitle ?: "",
                        requiredTwoBody = result.data?.termsList?.get(1)?.termsBody ?: "",
                        isMoreToSeeRequiredTwo = result.data?.termsList?.get(1)?.isMoreToSee ?: false,
                        requiredThreeTitle = result.data?.termsList?.get(2)?.termsTitle ?: "",
                        requiredThreeBody = result.data?.termsList?.get(2)?.termsBody ?: "",
                        isMoreToSeeRequiredThree = result.data?.termsList?.get(2)?.isMoreToSee ?: false,
                        requiredFourTitle = result.data?.termsList?.get(3)?.termsTitle ?: "",
                        requiredFourBody = result.data?.termsList?.get(3)?.termsBody ?: "",
                        isMoreToSeeRequiredFour = result.data?.termsList?.get(3)?.isMoreToSee ?: false,
                        choiceTitle = result.data?.termsList?.get(4)?.termsTitle ?: "",
                        choiceBody = result.data?.termsList?.get(4)?.termsBody ?: "",
                        isMoreToSeeChoice = result.data?.termsList?.get(4)?.isMoreToSee ?: false,
                    )
                }
                is Resource.Error ->{
                    stateTermsForm = TermsFormState(error = result.message ?:"An unexpeted error occured")
                   // _stateTerms.value = TermsListState(error = result.message ?:"An unexpeted error occured")
                }
                is Resource.Loading ->{
                    stateTermsForm = TermsFormState(isLoading = true)
                    //_stateTerms.value = TermsListState(isLoading = true)
                }
                else -> {}
            }
        }.launchIn(viewModelScope)
    }
    private fun getBeverages(){
        getBeveragesUseCase().onEach {result ->
            when(result){
                is Resource.Success ->{
                    _statePreferences.value = BeveragesListState(
                        beverageList = result.data?.beverageCategoryList ?: emptyList(),
                        size = result.data?.size  ?: 0
                    )
                }
                is Resource.Error ->{
                    _statePreferences.value = BeveragesListState(error = result.message ?: "An unexpeted error occured")
                }
                is Resource.Loading ->{
                    _statePreferences.value = BeveragesListState(isLoading = true)
                }

                else -> {}
            }
        }.launchIn(viewModelScope)
    }
    private fun getNickname() {
        getNicknameUseCase(stateNicknameForm.nickname).onEach{ result ->
            when(result){
                is Resource.Success ->{
                    if(result?.data?.code ?: 0 == 2011){ //닉네임 가능
                        stateNicknameForm = NicknameFormState(
                            isTried = true,
                            isError = false,
                            isSuccess = true,
                            successMessage = result.data?.message ?: "",
                            btnEnabled = true
                        )
                    } else { //닉네임 불가능, 2010:닉네임 중복, 4019:중복, 4020:욕
                        stateNicknameForm = NicknameFormState(
                            isTried = true,
                            isSuccess = false,
                            isError = true,
                            errorMessage = result.data?.message ?: "",
                            btnEnabled = false
                        )
                    }
                   /* if(result.data?.code ?: 0 == 2011){ //닉네임 가능
                        _stateCorrectMessage.value = result.data?.message ?: ""
                        _stateIsCorrect.value = true
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
                    Log.e("nickname-viewmodel", "correctmessage : ${_stateCorrectMessage.value}")*/
                    Log.e("nickname-viewmodel","${stateNicknameForm}")
                }
                is Resource.Error ->{
                    stateNicknameForm = NicknameFormState(error = result.message ?: "An unexpeted error occured")
                }
                is Resource.Loading->{
                    stateNicknameForm = NicknameFormState(isLoading = true)
                }
                else -> {}
            }
        }.launchIn(viewModelScope)
    }
    private fun getPhoneSms(){

    }




    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "cleared")
    }
}