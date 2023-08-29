package com.zipdabang.zipdabang_android.module.sign_up.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.core.data_store.proto.ProtoDataViewModel
import com.zipdabang.zipdabang_android.core.data_store.proto.ProtoRepository
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
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
import com.zipdabang.zipdabang_android.module.sign_up.domain.usecase.ValidateNicknameUseCase
import com.zipdabang.zipdabang_android.module.sign_up.domain.usecase.ValidatePhoneUseCase
import com.zipdabang.zipdabang_android.module.sign_up.domain.usecase.ValidationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class AuthSharedViewModel @Inject constructor(
    private val getTermsUseCase: GetTermsUseCase,
    private val getBeveragesUseCase: GetBeveragesUseCase,
    private val getNicknameUseCase: GetNicknameUseCase,
    private val postPhoneSmsUseCase: PostPhoneSmsUseCase,
    private val postAuthUseCase: PostAuthUseCase,
    private val postInfoUseCase: PostInfoUseCase,
    private val validateBirthdayUseCase : ValidateBirthdayUseCase = ValidateBirthdayUseCase(),
    private val validatePhoneUseCase: ValidatePhoneUseCase = ValidatePhoneUseCase(),
    private val validateNicknameUseCase: ValidateNicknameUseCase = ValidateNicknameUseCase(),
    private val dataStore: DataStore<Token>
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

    //social login platform
    lateinit var social : String
    suspend fun updateSocial(){
        social = dataStore.data.first().platformStatus.toString()
    }


    // info 글자수 제한하기, 생년월일 더 제한, 카카오 주소 api, response enum class로 옮기기, textfield 옮겨갈때마다 focusing


    /*TermsScreen*/
    var stateTermsForm by mutableStateOf(TermsFormState())
    fun onTermsEvent(event : TermsFormEvent){
        when(event){
            is TermsFormEvent.AllAgreeChanged -> {
                stateTermsForm = stateTermsForm.copy(allAgree = !stateTermsForm.allAgree)
                clickAllAgree()
            }
            is TermsFormEvent.RequiredOneChanged -> {
                stateTermsForm = stateTermsForm.copy(requiredOne = !stateTermsForm.requiredOne)
                updateAllagree()
            }
            is TermsFormEvent.RequiredTwoChanged -> {
                stateTermsForm = stateTermsForm.copy(requiredTwo = !stateTermsForm.requiredTwo)
                updateAllagree()
            }
            is TermsFormEvent.RequiredThreeChanged ->{
                stateTermsForm = stateTermsForm.copy(requiredThree = !stateTermsForm.requiredThree)
                updateAllagree()
            }
            is TermsFormEvent.RequiredFourChanged -> {
                stateTermsForm = stateTermsForm.copy(requiredFour = !stateTermsForm.requiredFour)
                updateAllagree()
            }
            is TermsFormEvent.ChoiceChanged -> {
                stateTermsForm = stateTermsForm.copy(choice = !stateTermsForm.choice)
                updateAllagree()
            }
            is TermsFormEvent.BtnChanged -> {
                updateValidationTerms()
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
                val nicknameResult = validateNicknameUseCase(stateNicknameForm.nickname)

                if(nicknameResult.successful){
                    getNickname()
                } else {
                    stateNicknameForm = stateNicknameForm.copy(
                        isTried = true,
                        isError = true,
                        errorMessage = nicknameResult.errorMessage
                    )
                }
            }
        }
    }



    /*UserInfoScreen*/
    var stateUserInfoForm by mutableStateOf(UserInfoFormState())
    var genderList by mutableStateOf(listOf("남", "여"))
    var remainingTime by mutableStateOf(0)
    fun onUserInfoEvent(event : UserInfoFormEvent){
        when(event){
            is UserInfoFormEvent.NameChanged -> {
               stateUserInfoForm = stateUserInfoForm.copy(name = event.name)
            }
            is UserInfoFormEvent.BirthdayChanged -> {
                stateUserInfoForm = stateUserInfoForm.copy(birthday = event.birthday)
                if(stateUserInfoForm.birthdayIsError){
                    stateUserInfoForm = stateUserInfoForm.copy(
                        birthday = event.birthday,
                        birthdayIsError = false,
                        birthdayErrorMessage = "",
                    )
                }
            }
            is UserInfoFormEvent.GenderChanged ->{
                stateUserInfoForm = stateUserInfoForm.copy(gender = event.gender)
            }
            is UserInfoFormEvent.PhoneNumberChanged->{
                stateUserInfoForm = stateUserInfoForm.copy(phoneNumber = event.phoneNumber)
                if(stateUserInfoForm.phoneNumberIsTried){
                    stateUserInfoForm = stateUserInfoForm.copy(
                        phoneNumber = event.phoneNumber,
                        phoneNumberIsTried = false,
                        phoneNumberIsCorrect = false,
                        phoneNumberCorrectMessage = "",
                    )
                }
                if(stateUserInfoForm.authNumberIsCorrect){
                    stateUserInfoForm = stateUserInfoForm.copy(
                        authNumber = "",
                        authNumberIsCorrect = false,
                        authNumberCorrectMessage = "",
                        phoneNumber = event.phoneNumber,
                        phoneNumberIsCorrect = false,
                        phoneNumberCorrectMessage = "",
                    )
                }
            }
            is UserInfoFormEvent.PhoneNumberClicked->{
                updateValidatePhonenumber()
            }
            is UserInfoFormEvent.AuthNumberChanged ->{
               stateUserInfoForm = stateUserInfoForm.copy(authNumber = event.authNumber)
                if(stateUserInfoForm.authNumberIsTried){
                    stateUserInfoForm = stateUserInfoForm.copy(
                        authNumber = event.authNumber,
                        authNumberIsTried = false,
                        authNumberCorrectMessage = "",
                        authNumberIsCorrect = false,
                    )
                }
            }
            is UserInfoFormEvent.AuthNumberClicked ->{
                //전화번호 여기 주석 풀어
                //postAuthNumber()
                stateUserInfoForm = stateUserInfoForm.copy(
                    phoneNumberCorrectMessage = "",
                    phoneNumberIsCorrect = false,
                )
            }
            is UserInfoFormEvent.BtnChanged -> {
                updateBtnEnabledUserInfo()
            }
            is UserInfoFormEvent.ValidateChanged ->{
                updateValidateUserInfo()
            }
        }
    }
    fun updateValidateBirthday() {
        val birthdayResult = validateBirthdayUseCase(stateUserInfoForm.birthday)
        stateUserInfoForm = stateUserInfoForm.copy(birthdayIsTried = true)

        if(birthdayResult.successful){
            stateUserInfoForm = stateUserInfoForm.copy(
                birthday = stateUserInfoForm.birthday,
                validate = true,
            )
        } else {
            stateUserInfoForm = stateUserInfoForm.copy(
                birthday = stateUserInfoForm.birthday,
                birthdayErrorMessage = birthdayResult.errorMessage,
                birthdayIsError = true,
                validate = false,
            )
        }
    }
    private fun updateValidatePhonenumber() {
        val phonenumberResult = validatePhoneUseCase(stateUserInfoForm.phoneNumber)

        if(phonenumberResult.successful){
            //전화번호 여기 주석 풀어
            //postPhonenumber() //api 호출
        } else{
            stateUserInfoForm = stateUserInfoForm.copy(
                phoneNumberIsTried = true,
                phoneNumberIsError = true,
                phoneNumberErrorMessage = phonenumberResult.errorMessage,
                authNumber = "",
                authNumberIsCorrect = false,
                authNumberCorrectMessage = "",
            )
            remainingTime = 0
        }
    }
    private fun updateBtnEnabledUserInfo(){
        val isFull = listOf(
            stateUserInfoForm.name,
            stateUserInfoForm.birthday,
        ).all{ it!="" }

        val isError = stateUserInfoForm.birthdayIsError

        //전화번호 여기 주석 풀어
        //val isValidateAtPhone = stateUserInfoForm.authNumberIsCorrect

        if (isFull && !isError){ //isFull && isValidateAtPhone && !isError
            stateUserInfoForm = stateUserInfoForm.copy(btnEnabled = true)
        } else {
            stateUserInfoForm = stateUserInfoForm.copy(btnEnabled = false)
        }
    }
    fun updateValidateUserInfo() : Boolean {
        var isCorrect = listOf(
            !stateUserInfoForm.birthdayIsError,
            //전화번호 여기 주석 풀어
            //stateUserInfoForm.authNumberIsCorrect
        ).all{ it==true }

        Log.e("userinfo-validate", "validate : ${stateUserInfoForm.validate} birtdayIsError : ${stateUserInfoForm.birthdayIsError}")

        if (isCorrect){
            stateUserInfoForm = stateUserInfoForm.copy(validate = true)
            return stateUserInfoForm.validate
            Log.e("userinfo-validate","validate true임")
        } else {
            stateUserInfoForm = stateUserInfoForm.copy(validate = false)
            return stateUserInfoForm.validate
            Log.e("userinfo-validate","validate false임")
        }

        return stateUserInfoForm.validate
    }



    /*UserAddressScreen*/
    var stateUserAddressForm by mutableStateOf(UserAddressFormState())
    fun onUserAddressEvent(event : UserAddressFormEvent){
        when (event){
            is UserAddressFormEvent.ZipcodeChanged ->{
                stateUserAddressForm = stateUserAddressForm.copy(zipCode = event.zipCode)
            }
            is UserAddressFormEvent.ZipcodeClicked ->{
                //웹뷰 띄우기
            }
            is UserAddressFormEvent.AddressChanged ->{
                stateUserAddressForm = stateUserAddressForm.copy(address = event.address)
            }
            is UserAddressFormEvent.DetailaddressChanged ->{
                stateUserAddressForm = stateUserAddressForm.copy(detailAddress = event.detailAddress)
            }
            is UserAddressFormEvent.BtnChanged ->{
                updateBtnEnabledUserAddress()
            }
        }
    }
    private fun updateBtnEnabledUserAddress(){
        val isFull = listOf(
            stateUserAddressForm.zipCode,
            stateUserAddressForm.address,
        ).all{ it!="" }

        if (isFull){
            stateUserAddressForm = stateUserAddressForm.copy(btnEnabled = true)
        } else{
            stateUserAddressForm = stateUserAddressForm.copy(btnEnabled = false)
        }
    }



    /*PreferencesScreen*/
    var stateBeverageForm by mutableStateOf(BeverageFormState())
    fun onBeverageEvent(event : BeverageFormEvent){
        when(event){
            is BeverageFormEvent.BeverageCheckListChanged ->{
                val updatedCheckList = stateBeverageForm.beverageCheckList.toMutableList().apply {
                    set(event.index, event.checked)
                }
                stateBeverageForm = stateBeverageForm.copy(beverageCheckList = updatedCheckList)
                Log.e("beverage-viewemodel", "${stateBeverageForm.beverageCheckList.mapIndexedNotNull { index, isSelected ->
                    if (isSelected) index+1 else null
                }}")
            }
            is BeverageFormEvent.BtnChanged ->{
                updateBtnEnabledBeverage()
            }
        }
    }
    private fun updateBtnEnabledBeverage(){
        val isChecked = stateBeverageForm.beverageCheckList.any{ it }

        if (isChecked){
            stateBeverageForm = stateBeverageForm.copy(btnEnabled = true)
        } else{
            stateBeverageForm = stateBeverageForm.copy(btnEnabled = false)
        }
    }



    /*API*/
    init{
        getTerms()
        getBeverages()
    }
    private fun getTerms(){
        getTermsUseCase().onEach {result ->
            when(result) {
                is Resource.Success ->{
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
                        choiceId = result.data?.termsList?.get(4)?.termsId ?: 0,
                        choiceTitle = result.data?.termsList?.get(4)?.termsTitle ?: "",
                        choiceBody = result.data?.termsList?.get(4)?.termsBody ?: "",
                        isMoreToSeeChoice = result.data?.termsList?.get(4)?.isMoreToSee ?: false,
                        )
                    Log.e("terms-viewmodel", "성공 ${result.data?.termsList}")
                }
                is Resource.Error ->{
                    stateTermsForm = TermsFormState(error = result.message ?:"An unexpeted error occured")
                    Log.e("terms-viewmodel", "에러")
                }
                is Resource.Loading ->{
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
                    if(result?.data?.code ?: 0 == 2053){ //닉네임 가능
                        stateNicknameForm = NicknameFormState(
                            nickname = stateNicknameForm.nickname,
                            isTried = true,
                            isError = false,
                            isSuccess = true,
                            successMessage = result.data?.message ?: "",
                            btnEnabled = true
                        )
                    } else { //닉네임 불가능, 2052:닉네임 중복, 4019:형식, 4020:욕
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
                    stateBeverageForm = BeverageFormState(
                        beverageList = result.data?.beverageCategoryList ?: emptyList(),
                        size = result.data?.size ?: 0,
                        beverageCheckList = List(result.data?.size ?: 0) { false }
                    )
                    Log.e("preferences-viewmodel", "성공 ${result.data?.beverageCategoryList}")
                }
                is Resource.Error ->{
                    stateBeverageForm = BeverageFormState(error = result.message ?: "An unexpeted error occured")
                    Log.e("preferences-viewmodel", "에러")
                }
                is Resource.Loading ->{
                    stateBeverageForm = BeverageFormState(isLoading = true)
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
                        stateUserInfoForm = stateUserInfoForm.copy(
                            authNumber = "",
                            authNumberIsTried = false,
                            authNumberIsCorrect = false,
                            authNumberIsError = false,
                            authNumberCorrectMessage = "",
                            authNumberErrorMessage = "",
                            phoneNumberIsTried = true,
                            phoneNumberIsCorrect = true,
                            phoneNumberIsError = false,
                            phoneNumberErrorMessage = "",
                            phoneNumberCorrectMessage = result.data.message//"인증번호가 요청되었습니다"
                        )
                        remainingTime = 5 * 60
                    } else if(result.data?.code == 2054) { //이미 가입됨
                        stateUserInfoForm = stateUserInfoForm.copy(
                            authNumber = "",
                            authNumberIsTried = false,
                            authNumberIsCorrect = false,
                            authNumberIsError = false,
                            authNumberCorrectMessage = "",
                            authNumberErrorMessage = "",
                            phoneNumberIsTried = true,
                            phoneNumberIsCorrect = false,
                            phoneNumberIsError = true,
                            phoneNumberErrorMessage = result.data.message,
                        )
                        remainingTime = 0
                    } else{
                        //뭐지
                    }
                    Log.e("phonenumber-viewmodel","성공 : ${result.code}")
                }
                is Resource.Error -> {
                    stateUserInfoForm = stateUserInfoForm.copy(
                        authNumber = "",
                        authNumberIsTried = false,
                        authNumberIsCorrect = false,
                        authNumberIsError = false,
                        authNumberCorrectMessage = "",
                        authNumberErrorMessage = "",
                        error = result.message ?: "An unexpeted error occured",
                    )
                    Log.e("phonenumber-viewmodel","에러 : ${result.code}")
                }
                is Resource.Loading -> {
                    stateUserInfoForm = stateUserInfoForm.copy(
                        authNumber = "",
                        authNumberIsTried = false,
                        authNumberIsCorrect = false,
                        authNumberIsError = false,
                        authNumberCorrectMessage = "",
                        authNumberErrorMessage = "",
                        isLoading = true,
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
            when(result){
                is Resource.Success ->{
                    if(result.data?.code == 2000){ //성공
                        stateUserInfoForm = stateUserInfoForm.copy(
                            authNumberIsTried = true,
                            authNumberIsError = false,
                            authNumberIsCorrect = true,
                            authNumberCorrectMessage = result.data.message,
                            authNumberErrorMessage = "",
                        )
                        remainingTime = 0
                    } else if(result.data?.code == 4056){ //bad_request
                        stateUserInfoForm = stateUserInfoForm.copy(
                            authNumberIsTried = true,
                            authNumberIsError = true,
                            authNumberIsCorrect = false,
                            authNumberCorrectMessage = "",
                            authNumberErrorMessage =result.data.message
                        )
                    } else if(result.data?.code == 4057) { //인증번호 불일치
                        stateUserInfoForm = stateUserInfoForm.copy(
                            authNumberIsTried = true,
                            authNumberIsError = true,
                            authNumberIsCorrect = false,
                            authNumberCorrectMessage = "",
                            authNumberErrorMessage = result.data.message
                        )
                    } else if(result.data?.code == 4058){ //인증번호 시간초과
                        stateUserInfoForm = stateUserInfoForm.copy(
                            authNumberIsTried = true,
                            authNumberIsError = true,
                            authNumberIsCorrect = false,
                            authNumberCorrectMessage = "",
                            authNumberErrorMessage = result.data.message
                        )
                    } else{
                        //뭐지
                    }
                    Log.e("phonenumber-viewmodel","성공 : ${result.code}  ${stateUserInfoForm}")
                }
                is Resource.Error -> {
                    stateUserInfoForm = stateUserInfoForm.copy(
                        error = result.message ?: "An unexpeted error occured",
                    )
                    Log.e("phonenumber-viewmodel","에러 : ${result.code}")
                }
                is Resource.Loading ->{
                    stateUserInfoForm = stateUserInfoForm.copy(
                        isLoading = true,
                    )
                    Log.e("phonenumber-viewmodel","로딩중 : ${result.code}")
                }
            }
        }.launchIn(viewModelScope)
    }
    fun postInfo(
        //tokenStoreViewModel: ProtoDataViewModel,
    ){
        postInfoUseCase(
            social = social,
            infoRequest = InfoRequest(
                email = _email.value,
                profileUrl = _profile.value,
                agreeTermsIdList = listOf(stateTermsForm.choiceId),
                name = stateUserInfoForm.name,
                birth = stateUserInfoForm.birthday,
                phoneNum = "01075572261", //stateUserInfoForm.phoneNumber,
                gender = stateUserInfoForm.gender,
                zipCode = stateUserAddressForm.zipCode,
                address = stateUserAddressForm.address,
                detailAddress = stateUserAddressForm.detailAddress,
                nickname = stateNicknameForm.nickname,
                preferBeverages = stateBeverageForm.beverageCheckList.mapIndexedNotNull { index, isSelected ->
                    if (isSelected) index+1 else null
                },
            )
        ).onEach{result ->
            when(result){
                is Resource.Success ->{
                    if(result.data?.code == 2000){
                        //tokenStoreViewModel.updateAccessToken(result.data.result.accessToken)
                        //tokenStoreViewModel.updateRefreshToken(result.data.result.refreshToken)
                    } else{
                        //토큰 null임 뭐지?
                    }
                    Log.e("signup-token", "성공 : ${result.data?.result}")
                }
                is Resource.Error ->{
                    Log.e("signup-token", "에러 : ${result.message} ${social} ${InfoRequest(
                        email = _email.value,
                        profileUrl = _profile.value,
                        agreeTermsIdList = listOf(stateTermsForm.choiceId),
                        name = stateUserInfoForm.name,
                        birth = stateUserInfoForm.birthday,
                        phoneNum = "01075572261", //stateUserInfoForm.phoneNumber,
                        gender = stateUserInfoForm.gender,
                        zipCode = stateUserAddressForm.zipCode,
                        address = stateUserAddressForm.address,
                        detailAddress = stateUserAddressForm.detailAddress,
                        nickname = stateNicknameForm.nickname,
                        preferBeverages = stateBeverageForm.beverageCheckList.mapIndexedNotNull { index, isSelected ->
                            if (isSelected) index+1 else null
                        },
                    )}")
                }
                is Resource.Loading ->{
                    Log.e("signup-token", "로딩중 : ${result.data?.result}")
                }
            }
        }.launchIn(viewModelScope)
    }


    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "cleared")
    }
}