package com.zipdabang.zipdabang_android.module.sign_up.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.module.sign_up.domain.usecase.GetBeveragesUseCase
import com.zipdabang.zipdabang_android.module.sign_up.domain.usecase.GetNicknameUseCase
import com.zipdabang.zipdabang_android.module.sign_up.domain.usecase.GetTermsUseCase
import com.zipdabang.zipdabang_android.module.sign_up.domain.usecase.PostPhoneSmsUseCase
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
    private val postPhoneSmsUseCase: PostPhoneSmsUseCase,
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



    /*UserInfoScreen*/
    var stateUserInfoForm by mutableStateOf(UserInfoFormState())
    var genderList by mutableStateOf(listOf("남", "여"))
    fun onUserInfoEvent(event : UserInfoFormEvent){
        when(event){
            is UserInfoFormEvent.NameChanged -> {
                stateUserInfoForm = stateUserInfoForm.copy(name = event.name)
                Log.e("userinfo-viewmodel", "${stateUserInfoForm}")
            }
            is UserInfoFormEvent.BirthdayChanged -> {
                stateUserInfoForm = stateUserInfoForm.copy(birthday = event.birthday)
                Log.e("userinfo-viewmodel", "${stateUserInfoForm}")
            }
            is UserInfoFormEvent.GenderChanged ->{
                stateUserInfoForm = stateUserInfoForm.copy(gender = event.gender)
                Log.e("userinfo-viewmodel", "${stateUserInfoForm}")
            }
            is UserInfoFormEvent.PhoneNumberChanged->{
                stateUserInfoForm = stateUserInfoForm.copy(phoneNumber = event.phoneNumber)
                Log.e("userinfo-viewmodel", "${stateUserInfoForm}")
            }
            is UserInfoFormEvent.AuthNumberChanged ->{
                stateUserInfoForm = stateUserInfoForm.copy(authNumber = event.authNumber)
                Log.e("userinfo-viewmodel", "${stateUserInfoForm}")
            }
            is UserInfoFormEvent.BtnChanged -> {

            }
        }
    }
    fun btnPhonenumberClicked(){
        postPhonenumber()
    }
    fun btnAuthnumberClicked(){

    }


    /*UserAddressScreen*/
    var stateUserAddressForm by mutableStateOf(UserAddressFormState())
    fun onUserAddressEvent(event : UserAddressFormEvent){
        when (event){
            is UserAddressFormEvent.ZipcodeChanged ->{
                stateUserAddressForm = stateUserAddressForm.copy(zipCode = event.zipCode)
                Log.e("useraddress-viewmodel", "${stateUserAddressForm}")
            }
            is UserAddressFormEvent.AddressChanged ->{
                stateUserAddressForm = stateUserAddressForm.copy(address = event.address)
                Log.e("useraddress-viewmodel", "${stateUserAddressForm}")
            }
            is UserAddressFormEvent.DetailaddressChanged ->{
                stateUserAddressForm = stateUserAddressForm.copy(detailAddress = event.detailAddress)
                Log.e("useraddress-viewmodel", "${stateUserAddressForm}")
            }
            is UserAddressFormEvent.BtnChanged ->{

            }
        }
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
    //private val _stateTerms = mutableStateOf(TermsFormState())
    //val stateTerms : State<TermsFormState> = _stateTerms
    //preferences - GET api
    private val _statePreferences = mutableStateOf(BeveragesListState(beverageList = emptyList()))
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

                    /* stateTermsForm = TermsFormState(
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
                        )*/
                    Log.e("terms-viewmodel", "성공 ${result.data?.termsList}")
                }
                is Resource.Error ->{
                   //_stateTerms.value = TermsListState(error = result.message ?:"An unexpeted error occured")
                    stateTermsForm = TermsFormState(error = result.message ?:"An unexpeted error occured")
                    Log.e("terms-viewmodel", "에러")
                }
                is Resource.Loading ->{
                    //_stateTerms.value = TermsListState(isLoading = true)
                    stateTermsForm = TermsFormState(isLoading = true)
                    Log.e("terms-viewmodel", "로딩중")
                }
            }
        }.launchIn(viewModelScope)
    }
    private fun getNickname() {
        getNicknameUseCase(stateNicknameForm.nickname).onEach{ result ->
            when(result){
                is Resource.Success ->{
                    if(result?.data?.code ?: 0 == 2011){ //닉네임 가능
                        stateNicknameForm = NicknameFormState(
                            nickname = stateNicknameForm.nickname,
                            isTried = true,
                            isError = false,
                            isSuccess = true,
                            successMessage = result.data?.message ?: "",
                            btnEnabled = true
                        )
                    } else { //닉네임 불가능, 2010:닉네임 중복, 4019:중복, 4020:욕
                        stateNicknameForm = NicknameFormState(
                            nickname = stateNicknameForm.nickname,
                            isTried = true,
                            isSuccess = false,
                            isError = true,
                            errorMessage = result.data?.message ?: "",
                            btnEnabled = false
                        )
                    }
                    Log.e("nickname-viewmodel","${stateNicknameForm}")
                }
                is Resource.Error ->{
                    stateNicknameForm = NicknameFormState(
                        nickname = stateNicknameForm.nickname,
                        error = result.message ?: "An unexpeted error occured"
                    )
                }
                is Resource.Loading->{
                    stateNicknameForm = NicknameFormState(
                        isLoading = true,
                        nickname = stateNicknameForm.nickname
                    )
                }
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
                    Log.e("preferences-viewmodel", "성공 ${result.data?.beverageCategoryList}")
                }
                is Resource.Error ->{
                    _statePreferences.value = BeveragesListState(error = result.message ?: "An unexpeted error occured")
                    Log.e("preferences-viewmodel", "에러")
                }
                is Resource.Loading ->{
                    _statePreferences.value = BeveragesListState(isLoading = true)
                    Log.e("preferences-viewmodel", "로딩중")
                }
            }
        }.launchIn(viewModelScope)
    }
    private fun postPhonenumber(){
        postPhoneSmsUseCase(stateUserInfoForm.phoneNumber).onEach{ result ->
            when(result) {
                is Resource.Success -> {
                    stateUserInfoForm = UserInfoFormState(
                        phoneNumber = stateUserInfoForm.phoneNumber
                    )
                    Log.e("phonenumber-viewmodel","성공 : ${result.code}")
                }
                is Resource.Error -> {
                    stateUserInfoForm = UserInfoFormState(
                        error = result.message ?: "An unexpeted error occured",
                        phoneNumber =  stateUserInfoForm.phoneNumber)
                    Log.e("phonenumber-viewmodel","에러 : ${result.code} ${result.message}")
                }
                is Resource.Loading -> {
                    stateUserInfoForm = UserInfoFormState(
                        isLoading = true,
                        phoneNumber = stateUserInfoForm.phoneNumber
                    )
                    Log.e("phonenumber-viewmodel","로딩중 : ${result.code}")
                }
            }
        }.launchIn(viewModelScope)
    }




    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "cleared")
    }
}