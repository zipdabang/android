package com.zipdabang.zipdabang_android.module.sign_up.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.core.data_store.proto.ProtoDataViewModel
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.AuthRequest
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.InfoRequest
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.PhoneRequest
import com.zipdabang.zipdabang_android.module.sign_up.domain.usecase.GetBeveragesUseCase
import com.zipdabang.zipdabang_android.module.sign_up.domain.usecase.GetNicknameUseCase
import com.zipdabang.zipdabang_android.module.sign_up.domain.usecase.GetTermsUseCase
import com.zipdabang.zipdabang_android.module.sign_up.domain.usecase.PostAuthUseCase
import com.zipdabang.zipdabang_android.module.sign_up.domain.usecase.PostInfoUseCase
import com.zipdabang.zipdabang_android.module.sign_up.domain.usecase.PostPhoneSmsUseCase
import com.zipdabang.zipdabang_android.module.sign_up.domain.usecase.ValidateBirthdayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AuthSharedViewModel @Inject constructor(
    //private val tokenStoreViewModel: ProtoDataViewModel,
    private val getTermsUseCase: GetTermsUseCase,
    private val getBeveragesUseCase: GetBeveragesUseCase,
    private val getNicknameUseCase: GetNicknameUseCase,
    private val postPhoneSmsUseCase: PostPhoneSmsUseCase,
    private val postAuthUseCase: PostAuthUseCase,
    private val postInfoUseCase: PostInfoUseCase,
    private val validateBirthdayUseCase : ValidateBirthdayUseCase = ValidateBirthdayUseCase(),
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
                clickAllAgree()
            }
            is TermsFormEvent.RequiredOneChanged -> {
                stateTermsForm = stateTermsForm.copy(requiredOne = !stateTermsForm.requiredOne)
                Log.e("termsAgree-viewmodel", "stateTermsAllagree: ${stateTermsForm}")
                updateAllagree()
            }
            is TermsFormEvent.RequiredTwoChanged -> {
                stateTermsForm = stateTermsForm.copy(requiredTwo = !stateTermsForm.requiredTwo)
                Log.e("termsAgree-viewmodel", "stateTermsAllagree: ${stateTermsForm}")
                updateAllagree()
            }
            is TermsFormEvent.RequiredThreeChanged ->{
                stateTermsForm = stateTermsForm.copy(requiredThree = !stateTermsForm.requiredThree)
                Log.e("termsAgree-viewmodel", "stateTermsAllagree: ${stateTermsForm}")
                updateAllagree()
            }
            is TermsFormEvent.RequiredFourChanged -> {
                stateTermsForm = stateTermsForm.copy(requiredFour = !stateTermsForm.requiredFour)
                Log.e("termsAgree-viewmodel", "stateTermsAllagree: ${stateTermsForm}")
                updateAllagree()
            }
            is TermsFormEvent.ChoiceChanged -> {
                stateTermsForm = stateTermsForm.copy(choice = !stateTermsForm.choice)
                Log.e("termsAgree-viewmodel", "stateTermsAllagree: ${stateTermsForm}")
                updateAllagree()
            }
            is TermsFormEvent.BtnChanged -> {
                updateValidationTerms()
                Log.e("termsAgree-viewmodel", "stateTermsAllagree: ${stateTermsForm}")
            }
        }
    }
    //TermsEvent 관리
    private fun updateValidationTerms() {
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
                if(stateNicknameForm.isSuccess || stateNicknameForm.isError){
                    stateNicknameForm = stateNicknameForm.copy(
                        nickname = event.nickname,
                        isError = false,
                        isSuccess = false,
                        errorMessage = "",
                        successMessage = "",
                        btnEnabled = false
                    )
                }
            }
            is NicknameFormEvent.NicknameCliked ->{
                getNickname()
            }
        }
    }



    /*UserInfoScreen*/
    var stateUserInfoForm by mutableStateOf(UserInfoFormState())
    var genderList by mutableStateOf(listOf("남", "여"))
    fun onUserInfoEvent(event : UserInfoFormEvent){
        when(event){
            is UserInfoFormEvent.NameChanged -> {
               stateUserInfoForm = stateUserInfoForm.copy(name = event.name)
            }
            is UserInfoFormEvent.BirthdayChanged -> {
                stateUserInfoForm = stateUserInfoForm.copy(birthday = event.birthday)
            }
            is UserInfoFormEvent.GenderChanged ->{
                stateUserInfoForm = stateUserInfoForm.copy(gender = event.gender)
            }
            is UserInfoFormEvent.PhoneNumberChanged->{
                stateUserInfoForm = stateUserInfoForm.copy(phoneNumber = event.phoneNumber)
            }
            is UserInfoFormEvent.PhoneNumberClicked->{
                postPhonenumber()
            }
            is UserInfoFormEvent.TimerCount ->{

            }
            is UserInfoFormEvent.AuthNumberChanged ->{
               stateUserInfoForm = stateUserInfoForm.copy(authNumber = event.authNumber)
            }
            is UserInfoFormEvent.AuthNumberClicked ->{
                postAuthNumber()
                //Log.e("userinfo-viewmodel", "${stateUserInfoForm}")
            }
            is UserInfoFormEvent.BtnChanged -> {
                //updateBtnEnabled()
            }
            is UserInfoFormEvent.ValidateChanged ->{
                //updateValidateUserInfo()
                //Log.e("userinfo-viewmodel", "${stateUserInfoForm}")
            }
        }
    }
    private fun updateValidateBirthday() {
        val birthdayResult = validateBirthdayUseCase(stateUserInfoForm.birthday)

        if(birthdayResult.successful){
            stateUserInfoForm = stateUserInfoForm.copy(
                birthday = stateUserInfoForm.birthday
            )
        } else {
            stateUserInfoForm = stateUserInfoForm.copy(
                birthday = stateUserInfoForm.birthday,
                birthdayErrorMessage = birthdayResult.errorMessage,
                birthdayIsError = birthdayResult.successful,
            )
        }
    }
    /*private fun updateBtnEnabled(){
        Log.e("userinfo-viewmodel", "${stateUserInfoForm}")

        val isFull = listOf(
            stateUserInfoForm.name,
            stateUserInfoForm.birthday,
            stateUserInfoForm.phoneNumber,
            stateUserInfoForm.authNumber,
        ).all{ it!="" }

        if (isFull){
            stateUserInfoForm = stateUserInfoForm.copy(btnEnabled = true)
        } else {
            stateUserInfoForm = stateUserInfoForm.copy(btnEnabled = false)
        }
    }*/
    /*private fun updateValidateUserInfo() {
        updateValidateBirthday()

        val isError = listOf(
            stateUserInfoForm.birthdayIsError,
            stateUserInfoForm.phoneNumberIsError,
            stateUserInfoForm.authNumberIsError
        ).any{ it==true }

        if (isError){
            stateUserInfoForm = stateUserInfoForm.copy(validate = false)
        } else {
            stateUserInfoForm = stateUserInfoForm.copy(validate = true)
        }
    }*/



    /*UserAddressScreen*/
    var stateUserAddressForm by mutableStateOf(UserAddressFormState())
    fun onUserAddressEvent(event : UserAddressFormEvent){
        when (event){
            is UserAddressFormEvent.ZipcodeChanged ->{
                stateUserAddressForm = stateUserAddressForm.copy(zipCode = event.zipCode)
                Log.e("useraddress-viewmodel", "${stateUserAddressForm}")
            }
            is UserAddressFormEvent.ZipcodeClicked ->{
                //웹뷰 띄우기
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
        postPhoneSmsUseCase(PhoneRequest(targetPhoneNum = stateUserInfoForm.phoneNumber)).onEach{ result ->
            when(result) {
                is Resource.Success -> {
                    if(result.data?.code == 2000){ //성공
                        stateUserInfoForm = UserInfoFormState(
                            name = stateUserInfoForm.name,
                            birthday = stateUserInfoForm.birthday,
                            gender = stateUserInfoForm.gender,
                            phoneNumber = stateUserInfoForm.phoneNumber,
                            phoneNumberIsTried = true,
                            phoneNumberIsCorrect = true,
                            phoneNumberCorrectMessage = "인증번호가 요청되었습니다.",
                            phoneNumberIsError = false,
                        )
                    } else if(result.data?.code == 2020) { //이미 가입됨
                        stateUserInfoForm = UserInfoFormState(
                            name = stateUserInfoForm.name,
                            birthday = stateUserInfoForm.birthday,
                            gender = stateUserInfoForm.gender,
                            phoneNumber = stateUserInfoForm.phoneNumber,
                            phoneNumberIsTried = true,
                            phoneNumberIsError = true,
                            phoneNumberErrorMessage = result.data.message,
                            phoneNumberIsCorrect = false,
                        )
                    } else{
                        //뭐지
                    }
                    Log.e("phonenumber-viewmodel","성공 : ${result.code} ${result.data?.message}")
                }
                is Resource.Error -> {
                    stateUserInfoForm = UserInfoFormState(
                        name = stateUserInfoForm.name,
                        birthday = stateUserInfoForm.birthday,
                        gender = stateUserInfoForm.gender,
                        error = result.message ?: "An unexpeted error occured",
                        phoneNumber =  stateUserInfoForm.phoneNumber
                    )
                    Log.e("phonenumber-viewmodel","에러 : ${result.code} ${result.message}")
                }
                is Resource.Loading -> {
                    stateUserInfoForm = UserInfoFormState(
                        name = stateUserInfoForm.name,
                        birthday = stateUserInfoForm.birthday,
                        gender = stateUserInfoForm.gender,
                        isLoading = true,
                        phoneNumber = stateUserInfoForm.phoneNumber
                    )
                    Log.e("phonenumber-viewmodel","로딩중 : ${result.code}")
                }
            }
        }.launchIn(viewModelScope)
    }
    private fun postAuthNumber(){
        postAuthUseCase(AuthRequest(
            phoneNum = stateUserInfoForm.phoneNumber,
            authNum = Integer.parseInt(stateUserInfoForm.authNumber)
        )).onEach{ result ->
            Log.e("phonenumber-viewmodel","${stateUserInfoForm.authNumber.toInt()} ${Integer.parseInt(stateUserInfoForm.authNumber)}")

            when(result){
                is Resource.Success ->{
                    if(result.data?.code == 2000){ //성공
                        stateUserInfoForm = UserInfoFormState(
                            name = stateUserInfoForm.name,
                            birthday = stateUserInfoForm.birthday,
                            gender = stateUserInfoForm.gender,
                            phoneNumber = stateUserInfoForm.phoneNumber,
                            authNumber = stateUserInfoForm.authNumber,
                            authNumberIsTried = true,
                            authNumberIsError = false,
                            authNumberIsCorrect = true,
                            authNumberCorrectMessage = result.data.result.responseCode,
                        )
                    } else if(result.data?.code == 4200){ //bad_request
                        stateUserInfoForm = UserInfoFormState(
                            name = stateUserInfoForm.name,
                            birthday = stateUserInfoForm.birthday,
                            gender = stateUserInfoForm.gender,
                            phoneNumber = stateUserInfoForm.phoneNumber,
                            authNumber = stateUserInfoForm.authNumber,
                            authNumberIsTried = true,
                            authNumberIsError = true,
                            authNumberIsCorrect = false,
                            authNumberErrorMessage = result.data.result.responseCode
                        )
                    } else if(result.data?.code == 4201) { //인증번호 불일치
                        stateUserInfoForm = UserInfoFormState(
                            name = stateUserInfoForm.name,
                            birthday = stateUserInfoForm.birthday,
                            gender = stateUserInfoForm.gender,
                            phoneNumber = stateUserInfoForm.phoneNumber,
                            authNumber = stateUserInfoForm.authNumber,
                            authNumberIsTried = true,
                            authNumberIsError = true,
                            authNumberIsCorrect = false,
                            authNumberErrorMessage = result.data.result.responseCode
                        )
                    } else if(result.data?.code == 4202){ //인증번호 시간초과
                        stateUserInfoForm = UserInfoFormState(
                            name = stateUserInfoForm.name,
                            birthday = stateUserInfoForm.birthday,
                            gender = stateUserInfoForm.gender,
                            phoneNumber = stateUserInfoForm.phoneNumber,
                            authNumber = stateUserInfoForm.authNumber,
                            authNumberIsTried = true,
                            authNumberIsError = true,
                            authNumberIsCorrect = false,
                            authNumberErrorMessage = result.data.result.responseCode
                        )
                    } else{
                        //뭐지
                    }
                    Log.e("phonenumber-viewmodel","성공 : ${AuthRequest(
                        phoneNum = stateUserInfoForm.phoneNumber,
                        authNum = stateUserInfoForm.authNumber.toInt()
                    )}")
                    Log.e("phonenumber-viewmodel","성공 : ${result.data?.isSuccess} ${result.data?.code} ${result.data?.message} ${result.data?.result}")
                }
                is Resource.Error -> {
                    stateUserInfoForm = UserInfoFormState(
                        name = stateUserInfoForm.name,
                        birthday = stateUserInfoForm.birthday,
                        gender = stateUserInfoForm.gender,
                        phoneNumber = stateUserInfoForm.phoneNumber,
                        authNumber = stateUserInfoForm.authNumber,
                        error = result.message ?: "An unexpeted error occured",
                    )
                    Log.e("phonenumber-viewmodel","에러 : ${AuthRequest(
                        phoneNum = stateUserInfoForm.phoneNumber,
                        authNum = stateUserInfoForm.authNumber.toInt()
                    )}")
                    Log.e("phonenumber-viewmodel","에러 :  ${result.code} ${result.message} ${result.data?.isSuccess} ${result.data?.code} ${result.data?.message} ${result.data?.result}")
                }
                is Resource.Loading ->{
                    stateUserInfoForm = UserInfoFormState(
                        name = stateUserInfoForm.name,
                        birthday = stateUserInfoForm.birthday,
                        gender = stateUserInfoForm.gender,
                        phoneNumber = stateUserInfoForm.phoneNumber,
                        authNumber = stateUserInfoForm.authNumber,
                        isLoading = true,
                    )
                    Log.e("phonenumber-viewmodel","로딩중 : ${result.code}")
                }
            }
        }.launchIn(viewModelScope)
    }
    /*private fun postInfo(){
        postInfoUseCase(
            social = ,
            infoRequest = InfoRequest(
                email = _email.value,
                profileUrl = _profile.value,
                agreeTermsIdList = listOf(),
                name = stateUserInfoForm.name,
                birth = stateUserInfoForm.birthday,
                gender = stateUserInfoForm.gender,
                zipCode = stateUserAddressForm.zipCode,
                address = stateUserAddressForm.address,
                detailAddress = stateUserAddressForm.detailAddress,
                nickname = stateNicknameForm.nickname,
                preferBeverages = ,
            )
        ).onEach{result ->
            when(result){
                is Resource.Success ->{
                    if(result.data?.result?.accessToken != null){
                        tokenStoreViewModel.updateAccessToken(result.data.result.accessToken)
                        tokenStoreViewModel.updateRefreshToken(result.data.result.refreshToken)
                    } else{
                        //토큰 null임 뭐지?
                    }
                }
                is Resource.Error ->{
                    Log.e("token", "에러 : ${result.data?.result}")
                }
                is Resource.Loading ->{
                    Log.e("token", "로딩중 : ${result.data?.result}")
                }
            }
        }
    }*/

    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "cleared")
    }
}