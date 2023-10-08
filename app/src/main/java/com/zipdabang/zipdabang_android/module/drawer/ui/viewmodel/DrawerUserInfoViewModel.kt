package com.zipdabang.zipdabang_android.module.drawer.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zipdabang.zipdabang_android.common.Constants
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfodto.UserInfoBasicRequest
import com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfodto.UserInfoDetailRequest
import com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfodto.UserInfoNicknameRequest
import com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfodto.UserInfoPreferencesRequest
import com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfodto.UserInfoProfileRequest
import com.zipdabang.zipdabang_android.module.drawer.domain.usecase.GetUserInfoUseCase
import com.zipdabang.zipdabang_android.module.drawer.domain.usecase.PatchUserInfoBasicUseCase
import com.zipdabang.zipdabang_android.module.drawer.domain.usecase.PatchUserInfoDetailUseCase
import com.zipdabang.zipdabang_android.module.drawer.domain.usecase.PatchUserInfoNicknameUseCase
import com.zipdabang.zipdabang_android.module.drawer.domain.usecase.PatchUserInfoPreferencesUseCase
import com.zipdabang.zipdabang_android.module.drawer.domain.usecase.PatchUserInfoProfileUseCase
import com.zipdabang.zipdabang_android.module.drawer.ui.state.userinfo.UserInfoBasicEvent
import com.zipdabang.zipdabang_android.module.drawer.ui.state.userinfo.UserInfoBasicState
import com.zipdabang.zipdabang_android.module.drawer.ui.state.userinfo.UserInfoDetailEvent
import com.zipdabang.zipdabang_android.module.drawer.ui.state.userinfo.UserInfoDetailState
import com.zipdabang.zipdabang_android.module.drawer.ui.state.userinfo.UserInfoNicknameEvent
import com.zipdabang.zipdabang_android.module.drawer.ui.state.userinfo.UserInfoNicknameState
import com.zipdabang.zipdabang_android.module.drawer.ui.state.userinfo.UserInfoOneLineEvent
import com.zipdabang.zipdabang_android.module.drawer.ui.state.userinfo.UserInfoOneLineState
import com.zipdabang.zipdabang_android.module.drawer.ui.state.userinfo.UserInfoPreferencesEvent
import com.zipdabang.zipdabang_android.module.drawer.ui.state.userinfo.UserInfoPreferencesState
import com.zipdabang.zipdabang_android.module.drawer.ui.state.userinfo.UserInfoState
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.AuthRequest
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.PhoneRequest
import com.zipdabang.zipdabang_android.module.sign_up.domain.usecase.GetBeveragesUseCase
import com.zipdabang.zipdabang_android.module.sign_up.domain.usecase.GetNicknameUseCase
import com.zipdabang.zipdabang_android.module.sign_up.domain.usecase.PostAuthUseCase
import com.zipdabang.zipdabang_android.module.sign_up.domain.usecase.PostPhoneSmsUseCase
import com.zipdabang.zipdabang_android.module.sign_up.domain.usecase.ValidateBirthdayUseCase
import com.zipdabang.zipdabang_android.module.sign_up.domain.usecase.ValidateNicknameUseCase
import com.zipdabang.zipdabang_android.module.sign_up.domain.usecase.ValidatePhoneUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrawerUserInfoViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getNicknameUseCase: GetNicknameUseCase,
    private val getBeveragesUseCase: GetBeveragesUseCase,
    private val postPhoneSmsUseCase: PostPhoneSmsUseCase,
    private val postAuthUseCase: PostAuthUseCase,
    private val patchUserInfoBasicUseCase: PatchUserInfoBasicUseCase,
    private val patchUserInfoDetailUseCase: PatchUserInfoDetailUseCase,
    private val patchUserInfoNicknameUseCase: PatchUserInfoNicknameUseCase,
    private val patchUserInfoPreferencesUseCase: PatchUserInfoPreferencesUseCase,
    private val patchUserInfoProfileUseCase: PatchUserInfoProfileUseCase,
    private val validateBirthdayUseCase : ValidateBirthdayUseCase = ValidateBirthdayUseCase(),
    private val validatePhoneUseCase: ValidatePhoneUseCase = ValidatePhoneUseCase(),
    private val validateNicknameUseCase: ValidateNicknameUseCase = ValidateNicknameUseCase(),
    private val dataStore: DataStore<Token>
) : ViewModel() {

    var stateUserInfo by mutableStateOf(UserInfoState())
    var stateUserInfoBasic by mutableStateOf(UserInfoBasicState())
    var stateUserInfoDetail by mutableStateOf(UserInfoDetailState())
    var stateUserInfoNickname by mutableStateOf(UserInfoNicknameState())
    var stateUserInfoOneLine by mutableStateOf(UserInfoOneLineState())
    var stateUserInfoPreferences by mutableStateOf(UserInfoPreferencesState())
    var genderList by mutableStateOf(listOf("남", "여"))
    var remainingTime by mutableStateOf(0)


    /*UserInfoBasicScreen*/
    fun onUserInfoBasicEvent(event : UserInfoBasicEvent){
        when(event){
            is UserInfoBasicEvent.NameChanged ->{
                stateUserInfoBasic = stateUserInfoBasic.copy(name = event.name)
            }
            is UserInfoBasicEvent.BirthdayChanged ->{
                stateUserInfoBasic = stateUserInfoBasic.copy(birthday = event.birthday)
                if(stateUserInfoBasic.birthdayIsError){
                    stateUserInfoBasic = stateUserInfoBasic.copy(
                        birthday = event.birthday,
                        birthdayIsError = false,
                        birthdayErrorMessage = "",
                    )
                }
            }
            is UserInfoBasicEvent.GenderChanged ->{
                stateUserInfoBasic = stateUserInfoBasic.copy(gender = event.gender)
            }
            is UserInfoBasicEvent.PhoneNumberChanged ->{
                stateUserInfoBasic = stateUserInfoBasic.copy(phoneNumber = event.phoneNumber)
                if(stateUserInfoBasic.phoneNumberIsTried){
                    stateUserInfoBasic = stateUserInfoBasic.copy(
                        phoneNumber = event.phoneNumber,
                        phoneNumberIsTried = false,
                        phoneNumberIsCorrect = false,
                        phoneNumberCorrectMessage = "",
                    )
                }
                if(stateUserInfoBasic.authNumberIsCorrect){
                    stateUserInfoBasic = stateUserInfoBasic.copy(
                        authNumber = "",
                        authNumberIsCorrect = false,
                        authNumberCorrectMessage = "",
                        phoneNumber = event.phoneNumber,
                        phoneNumberIsCorrect = false,
                        phoneNumberCorrectMessage = "",
                    )
                }
            }
            is UserInfoBasicEvent.PhoneNumberClicked ->{
                updateValidatePhonenumber()
            }
            is UserInfoBasicEvent.AuthNumberChanged ->{
                stateUserInfoBasic = stateUserInfoBasic.copy(authNumber = event.authNumber)
                if(stateUserInfoBasic.authNumberIsTried){
                    stateUserInfoBasic = stateUserInfoBasic.copy(
                        authNumber = event.authNumber,
                        authNumberIsTried = false,
                        authNumberCorrectMessage = "",
                        authNumberIsCorrect = false,
                    )
                }
            }
            is UserInfoBasicEvent.AuthNumberClicked ->{
                //전화번호 여기 주석 풀어
                //postAuthNumber()
                stateUserInfoBasic = stateUserInfoBasic.copy(
                    phoneNumberCorrectMessage = "",
                    phoneNumberIsCorrect = false,
                )
            }
            is UserInfoBasicEvent.BtnEnabled ->{
                updateBtnEnabledUserInfoBasic()
            }
            is UserInfoBasicEvent.ValidateChanged ->{
                updateValidateUserInfoBasic()
            }
        }
    }
    fun updateValidateBirthday() {
        val birthdayResult = validateBirthdayUseCase(stateUserInfoBasic.birthday)
        stateUserInfoBasic = stateUserInfoBasic.copy(birthdayIsTried = true)

        if(birthdayResult.successful){
            stateUserInfoBasic = stateUserInfoBasic.copy(
                birthday = stateUserInfoBasic.birthday,
                validate = true,
            )
        } else {
            stateUserInfoBasic = stateUserInfoBasic.copy(
                birthday = stateUserInfoBasic.birthday,
                birthdayErrorMessage = birthdayResult.errorMessage,
                birthdayIsError = true,
                validate = false,
            )
        }
    }
    private fun updateValidatePhonenumber() {
        val phonenumberResult = validatePhoneUseCase(stateUserInfoBasic.phoneNumber)

        if(phonenumberResult.successful){
            //전화번호 여기 주석 풀어
            //postPhonenumber() //api 호출
        } else{
            stateUserInfoBasic = stateUserInfoBasic.copy(
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
    private fun updateBtnEnabledUserInfoBasic(){
        val isNameChanged = if(stateUserInfo.name != stateUserInfoBasic.name) true else false
        val isBirthdayChanged = if(stateUserInfo.birthday != stateUserInfoBasic.birthday) true else false
        val isGenderChanged = if(stateUserInfo.gender != stateUserInfoBasic.gender) true else false
        //val isPhonenumberChanged = if(stateUserInfo.phoneNumber != stateUserInfoBasic.phoneNumber) true else false
        val isAuthChecked = stateUserInfoBasic.authNumberIsCorrect

        val isChanged = listOf(
            isNameChanged,
            isBirthdayChanged,
            isGenderChanged,
            isAuthChecked
        ).any{ it }

        if(isChanged){
            stateUserInfoBasic = stateUserInfoBasic.copy(btnEnabled = true)
        } else{
            stateUserInfoBasic = stateUserInfoBasic.copy(btnEnabled = false)
        }
    }
    fun updateValidateUserInfoBasic() : Boolean{
        var isBirthdayCorrect = !stateUserInfoBasic.birthdayIsError

        if (isBirthdayCorrect){
            stateUserInfoBasic = stateUserInfoBasic.copy(validate = true)
            return stateUserInfoBasic.validate
        } else {
            stateUserInfoBasic = stateUserInfoBasic.copy(validate = false)
            return stateUserInfoBasic.validate
        }

        return stateUserInfoBasic.validate
    }


    /*UserInfoDetailScreen*/
    fun onUserInfoDetailEvent(event : UserInfoDetailEvent){
        when(event){
            is UserInfoDetailEvent.ZipcodeChanged ->{
                stateUserInfoDetail = stateUserInfoDetail.copy(zipCode = event.zipCode)
            }
            is UserInfoDetailEvent.ZipcodeClicked ->{
                //웹뷰 띄우기
            }
            is UserInfoDetailEvent.AddressChanged ->{
                stateUserInfoDetail = stateUserInfoDetail.copy(address = event.address)
            }
            is UserInfoDetailEvent.DetailaddressChanged ->{
                stateUserInfoDetail = stateUserInfoDetail.copy(detailAddress = event.detailAddress)
            }
            is UserInfoDetailEvent.BtnEnabled ->{

            }
        }
    }


    /*UserInfoNicknameScreen*/
    fun onUserInfoNicknameEvent(event : UserInfoNicknameEvent){
        when(event){
            is UserInfoNicknameEvent.NicknameChanged ->{
                stateUserInfoNickname = stateUserInfoNickname.copy(nickname = event.nickname)
                if(stateUserInfoNickname.isSuccess || stateUserInfoNickname.isError){
                    stateUserInfoNickname = stateUserInfoNickname.copy(
                        nickname = event.nickname,
                        isError = false,
                        isSuccess = false,
                        errorMessage = "",
                        successMessage = "",
                        btnEnabled = false
                    )
                }
            }
            is UserInfoNicknameEvent.NicknameClicked ->{
                val nicknameResult = validateNicknameUseCase(stateUserInfoNickname.nickname)
                if(nicknameResult.successful){
                    getNickname()
                } else {
                    stateUserInfoNickname = stateUserInfoNickname.copy(
                        isTried = true,
                        isError = true,
                        errorMessage = nicknameResult.errorMessage,
                        btnEnabled = false,
                    )
                }
            }
            is UserInfoNicknameEvent.BtnEnabled ->{
                stateUserInfoNickname = stateUserInfoNickname.copy(
                    btnEnabled = false
                )
            }
        }
    }


    /*UserInfoOneLineScreen*/
    fun onUserInfoOneLineEvent(event : UserInfoOneLineEvent){
        when(event){
            is UserInfoOneLineEvent.OneLineChanged ->{
                stateUserInfoOneLine = stateUserInfoOneLine.copy(
                    oneline = event.oneline
                )
            }
            is UserInfoOneLineEvent.BtnEnabled->{

            }
        }
    }


    /*UserInfoPreferencesScreen*/
    fun onUserInfoPreferencesEvent(event : UserInfoPreferencesEvent){
        when (event){
            is UserInfoPreferencesEvent.BeverageCheckListChanged ->{
                val updatedCheckList = stateUserInfoPreferences.preferBeverageCheckList.toMutableList().apply {
                    set(event.index, event.checked)
                }
                stateUserInfoPreferences = stateUserInfoPreferences.copy(preferBeverageCheckList = updatedCheckList)
            }
            is UserInfoPreferencesEvent.BtnEnabled -> {
                updateBtnEnabledPreferences()
            }
        }
    }
    private fun updateBtnEnabledPreferences(){
        if(stateUserInfo.preferBeverageCheckList != stateUserInfoPreferences.preferBeverageCheckList) {
            stateUserInfoPreferences = stateUserInfoPreferences.copy(btnEnabled = true)
        } else{
            stateUserInfoPreferences = stateUserInfoPreferences.copy(btnEnabled = false)
        }
    }


    /*api로 불러온 정보를 담은 state와 각 screen의 state와 매치시키는 함수*/
    fun onCheckedEvent(){
        if(stateUserInfo.name != stateUserInfoBasic.name){
            stateUserInfoBasic = stateUserInfoBasic.copy(name = stateUserInfo.name)
        }
        if(stateUserInfo.birthday != stateUserInfoBasic.birthday){
            stateUserInfoBasic = stateUserInfoBasic.copy(birthday = stateUserInfo.birthday)
        }
        if(stateUserInfo.gender != stateUserInfoBasic.gender){
            stateUserInfoBasic = stateUserInfoBasic.copy(gender = stateUserInfo.gender)
        }
        if(stateUserInfo.phoneNumber != stateUserInfoBasic.phoneNumber){
            stateUserInfoBasic = stateUserInfoBasic.copy(phoneNumber = stateUserInfo.phoneNumber)
        }

        if(stateUserInfo.zipcode != stateUserInfoDetail.zipCode){
            stateUserInfoDetail = stateUserInfoDetail.copy(zipCode = stateUserInfo.zipcode)
        }
        if(stateUserInfo.address != stateUserInfoDetail.address){
            stateUserInfoDetail = stateUserInfoDetail.copy(address = stateUserInfo.address)
        }
        if(stateUserInfo.detailAddress != stateUserInfoDetail.detailAddress){
            stateUserInfoDetail = stateUserInfoDetail.copy(detailAddress = stateUserInfo.detailAddress)
        }

        if(stateUserInfo.nickname != stateUserInfoNickname.nickname){
            stateUserInfoNickname = stateUserInfoNickname.copy(nickname = stateUserInfo.nickname)
        }

        if(stateUserInfo.preferBeverageCheckList != stateUserInfoPreferences.preferBeverageCheckList){
            val preferBeverageCheckList = stateUserInfoPreferences.preferBeverageList.map { category ->
                stateUserInfo.preferBeverageList.indexOf(category.categoryName).let { index ->
                    if (index != -1) true else false
                }
            }

            stateUserInfo = stateUserInfo.copy(preferBeverageCheckList = preferBeverageCheckList)

            stateUserInfoPreferences = stateUserInfoPreferences.copy(
                preferBeverageCheckList = preferBeverageCheckList,
                size = preferBeverageCheckList.size
            )
        }
    }


    init{
        getBeverages()
        viewModelScope.launch {
            getUserInfo()
        }
    }
    suspend fun getUserInfo(){
        val accessToken = "Bearer " + dataStore.data.first().accessToken ?: Constants.TOKEN_NULL
        //Log.e("drawer-userinfo-viewmodel","${accessToken}")
        getUserInfoUseCase(
            accessToken
        ).onEach { result->
            when(result){
                is Resource.Success -> {

                    stateUserInfo = stateUserInfo.copy(
                        isLoading = false,
                        email = result.data?.email ?: "",
                        profileUrl = result.data?.profileUrl ?: "",
                        name = result.data?.memberBasicInfoDto?.name ?: "",
                        birthday = result.data?.memberBasicInfoDto?.birth ?: "",
                        gender =  if(result.data?.memberBasicInfoDto?.genderType == "WOMAN") "여"
                        else if (result.data?.memberBasicInfoDto?.genderType == "MAN") "남" else "",
                        phoneNumber = result.data?.memberBasicInfoDto?.phoneNum ?: "",
                        preferBeverageList = result.data?.preferCategories?.categories?.map { it.name } ?: emptyList(),
                        preferBeverageCheckList = List(result.data?.preferCategories?.size ?: 0) { true },
                        size = result.data?.preferCategories?.size ?: 0,
                        zipcode = result.data?.memberDetailInfoDto?.zipCode ?: "",
                        address = result.data?.memberDetailInfoDto?.address ?: "",
                        detailAddress = result.data?.memberDetailInfoDto?.detailAddress ?: "",
                        nickname = result.data?.nickname ?: "",
                    )
                    stateUserInfoBasic = stateUserInfoBasic.copy(
                        isLoading = false,
                        name = result.data?.memberBasicInfoDto?.name ?: "",
                        birthday = result.data?.memberBasicInfoDto?.birth ?: "",
                        gender =  if(result.data?.memberBasicInfoDto?.genderType == "WOMAN") "여"
                        else if(result.data?.memberBasicInfoDto?.genderType == "MAN") "남" else "",
                        phoneNumber = result.data?.memberBasicInfoDto?.phoneNum ?: "",
                    )
                    stateUserInfoDetail = stateUserInfoDetail.copy(
                        isLoading = false,
                        zipCode = result.data?.memberDetailInfoDto?.zipCode ?: "",
                        address = result.data?.memberDetailInfoDto?.address ?: "",
                        detailAddress = result.data?.memberDetailInfoDto?.detailAddress ?: "",
                    )
                    stateUserInfoNickname = stateUserInfoNickname.copy(
                        isLoading = false,
                        nickname = result.data?.nickname ?: "",
                    )
                    stateUserInfoPreferences = stateUserInfoPreferences.copy(
                        isLoading = false,
                        size = result.data?.preferCategories?.size ?: 0,
                    )
                    Log.e("drawer-userinfo-viewmodel", "성공 ${result.data}")
                }
                is Resource.Error ->{
                    stateUserInfo = stateUserInfo.copy(error = result.message ?: "An unexpeted error occured")
                    stateUserInfoBasic = stateUserInfoBasic.copy(error = result.message ?: "An unexpeted error occured")
                    stateUserInfoDetail = stateUserInfoDetail.copy(error = result.message ?: "An unexpeted error occured")
                    stateUserInfoNickname = stateUserInfoNickname.copy(error = result.message ?: "An unexpeted error occured")
                    stateUserInfoPreferences = stateUserInfoPreferences.copy(error = result.message ?: "An unexpeted error occured")
                    Log.e("drawer-userinfo-viewmodel", "에러 ${result.code}")
                }
                is Resource.Loading -> {
                    stateUserInfo = stateUserInfo.copy(isLoading = true)
                    stateUserInfoBasic = stateUserInfoBasic.copy(isLoading = true)
                    stateUserInfoDetail = stateUserInfoDetail.copy(isLoading = true)
                    stateUserInfoNickname = stateUserInfoNickname.copy(isLoading = true)
                    stateUserInfoPreferences = stateUserInfoPreferences.copy(isLoading = true)
                    Log.e("drawer-userinfo-viewmodel", "로딩중 ${accessToken}")
                }
            }
        }.launchIn(viewModelScope)
    }
    private fun postPhonenumber(){
        postPhoneSmsUseCase(PhoneRequest(targetPhoneNum = stateUserInfoBasic.phoneNumber)).onEach{ result ->
            when(result) {
                is Resource.Success -> {
                    if(result.data?.code == 2000){ //성공
                        stateUserInfoBasic = stateUserInfoBasic.copy(
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
                        stateUserInfoBasic = stateUserInfoBasic.copy(
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
                    stateUserInfoBasic = stateUserInfoBasic.copy(
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
                    stateUserInfoBasic = stateUserInfoBasic.copy(
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
        postAuthUseCase(
            AuthRequest(
            phoneNum = stateUserInfoBasic.phoneNumber,
            authNum = Integer.parseInt(stateUserInfoBasic.authNumber)
        )
        ).onEach{ result ->
            when(result){
                is Resource.Success ->{
                    if(result.data?.code == 2000){ //성공
                        stateUserInfoBasic = stateUserInfoBasic.copy(
                            authNumberIsTried = true,
                            authNumberIsError = false,
                            authNumberIsCorrect = true,
                            authNumberCorrectMessage = result.data.message,
                            authNumberErrorMessage = "",
                        )
                        remainingTime = 0
                    } else if(result.data?.code == 4056){ //bad_request
                        stateUserInfoBasic = stateUserInfoBasic.copy(
                            authNumberIsTried = true,
                            authNumberIsError = true,
                            authNumberIsCorrect = false,
                            authNumberCorrectMessage = "",
                            authNumberErrorMessage =result.data.message
                        )
                    } else if(result.data?.code == 4057) { //인증번호 불일치
                        stateUserInfoBasic = stateUserInfoBasic.copy(
                            authNumberIsTried = true,
                            authNumberIsError = true,
                            authNumberIsCorrect = false,
                            authNumberCorrectMessage = "",
                            authNumberErrorMessage = result.data.message
                        )
                    } else if(result.data?.code == 4058){ //인증번호 시간초과
                        stateUserInfoBasic = stateUserInfoBasic.copy(
                            authNumberIsTried = true,
                            authNumberIsError = true,
                            authNumberIsCorrect = false,
                            authNumberCorrectMessage = "",
                            authNumberErrorMessage = result.data.message
                        )
                    } else{
                        //뭐지
                    }
                    Log.e("phonenumber-viewmodel","성공 : ${result.code}")
                }
                is Resource.Error -> {
                    stateUserInfoBasic = stateUserInfoBasic.copy(
                        error = result.message ?: "An unexpeted error occured",
                    )
                    Log.e("phonenumber-viewmodel","에러 : ${result.code}")
                }
                is Resource.Loading ->{
                    stateUserInfoBasic = stateUserInfoBasic.copy(
                        isLoading = true,
                    )
                    Log.e("phonenumber-viewmodel","로딩중 : ${result.code}")
                }
            }
        }.launchIn(viewModelScope)
    }
    private fun getNickname() {
        getNicknameUseCase(stateUserInfoNickname.nickname).onEach{ result ->
            when(result){
                is Resource.Success ->{
                    if(result?.data?.code ?: 0 == 2053){ //닉네임 가능
                        stateUserInfoNickname = stateUserInfoNickname.copy(
                            nickname = stateUserInfoNickname.nickname,
                            isTried = true,
                            isError = false,
                            isSuccess = true,
                            successMessage = result.data?.message ?: "",
                            btnEnabled = true
                        )
                    } else { //닉네임 불가능, 2052:닉네임 중복, 4019:형식, 4020:욕
                        stateUserInfoNickname = stateUserInfoNickname.copy(
                            nickname = stateUserInfoNickname.nickname,
                            isTried = true,
                            isSuccess = false,
                            isError = true,
                            errorMessage = result.data?.message ?: "",
                            btnEnabled = false
                        )
                    }
                    Log.e("nickname-viewmodel","${stateUserInfoNickname}")
                }
                is Resource.Error ->{
                    stateUserInfoNickname = stateUserInfoNickname.copy(
                        nickname = stateUserInfoNickname.nickname,
                        error = result.message ?: "An unexpeted error occured"
                    )
                }
                is Resource.Loading->{
                    stateUserInfoNickname = stateUserInfoNickname.copy(
                        isLoading = true,
                        nickname = stateUserInfoNickname.nickname
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
    private fun getBeverages(){
        getBeveragesUseCase().onEach {result ->
            when(result){
                is Resource.Success ->{
                    stateUserInfoPreferences = stateUserInfoPreferences.copy(
                        isLoading= false,
                        preferBeverageList = result.data?.beverageCategoryList ?: emptyList(),
                        size = result.data?.size ?: 0,
                        preferBeverageCheckList = List(result.data?.size ?: 0) { false }
                    )
                    Log.e("preferences-viewmodel", "성공 ${result.data?.beverageCategoryList}")
                }
                is Resource.Error ->{
                    stateUserInfoPreferences = stateUserInfoPreferences.copy(error = result.message ?: "An unexpeted error occured")
                    Log.e("preferences-viewmodel", "에러")
                }
                is Resource.Loading ->{
                    stateUserInfoPreferences = stateUserInfoPreferences.copy(isLoading = true)
                    Log.e("preferences-viewmodel", "로딩중")
                }
            }
        }.launchIn(viewModelScope)
    }
    suspend fun patchUserInfoBasic(){
        try{
            val result = patchUserInfoBasicUseCase(
                accessToken = "Bearer " + dataStore.data.first().accessToken.toString(),
                userInfoBasic = UserInfoBasicRequest(
                    name = stateUserInfoBasic.name,
                    birth = stateUserInfoBasic.birthday,
                    genderType = if(stateUserInfoBasic.gender == "여") "WOMAN" else "MAN",
                    phoneNum = stateUserInfoBasic.phoneNumber,
                ),
            )
            result.collect { result->
                when(result){
                    is Resource.Success ->{
                        Log.e("nicknameedit-viewmodel","성공 : ${result.data?.status}  ${result.data?.memberId}  ${result.data?.calledAt}")
                    }
                    is Resource.Error ->{
                        stateUserInfoBasic = stateUserInfoBasic.copy(error = result.message ?: "An unexpeted error occured")
                        Log.e("nicknameedit-viewmodel","에러 :  ${result.code} ${result.message}")
                    }
                    is Resource.Loading ->{
                        stateUserInfoBasic = stateUserInfoBasic.copy(isLoading = true)
                        Log.e("nicknameedit-viewmodel","로딩중 :  ${result.code} ${result.message}")
                    }
                }
            }
            getUserInfo()
        } catch (e: Exception) {}
    }
    suspend fun patchUserInfoDetail(){
        try{
            val result = patchUserInfoDetailUseCase(
                accessToken = "Bearer " +dataStore.data.first().accessToken.toString(),
                userInfoDetail = UserInfoDetailRequest(
                    zipCode = stateUserInfoDetail.zipCode,
                    address = stateUserInfoDetail.address,
                    detailAddress = stateUserInfoDetail.detailAddress
                ),
            )
            result.collect { result->
                when(result){
                    is Resource.Success ->{
                        Log.e("nicknameedit-viewmodel","성공 : ${result.data?.status}  ${result.data?.memberId}  ${result.data?.calledAt}")
                    }
                    is Resource.Error ->{
                        stateUserInfoDetail = stateUserInfoDetail.copy(error = result.message ?: "An unexpeted error occured")
                        Log.e("nicknameedit-viewmodel","에러 :  ${result.code} ${result.message}")
                    }
                    is Resource.Loading ->{
                        stateUserInfoDetail = stateUserInfoDetail.copy(isLoading = true)
                        Log.e("nicknameedit-viewmodel","로딩중 :  ${result.code} ${result.message}")
                    }
                }
            }
            getUserInfo()
        } catch (e: Exception) {}
    }
    suspend fun patchUserInfoNickname(){
        try{
            Log.e("nicknameedit-viewmodel","토큰, 닉네임 :  ${dataStore.data.first().accessToken.toString()} ${stateUserInfoNickname.nickname}")

            val result = patchUserInfoNicknameUseCase(
                accessToken = "Bearer " + dataStore.data.first().accessToken.toString(),
                userInfoNickname = UserInfoNicknameRequest(stateUserInfoNickname.nickname),
            )
            result.collect { result->
                when(result){
                    is Resource.Success ->{
                        stateUserInfoNickname = stateUserInfoNickname.copy(
                            isTried = false,
                            isError = false,
                            isSuccess = false,
                            btnEnabled = false,
                        )
                        Log.e("nicknameedit-viewmodel","성공 : ${result.data?.status}  ${result.data?.memberId}  ${result.data?.calledAt}")
                    }
                    is Resource.Error ->{
                        stateUserInfoNickname = stateUserInfoNickname.copy(error = result.message ?: "An unexpeted error occured")
                        Log.e("nicknameedit-viewmodel","에러 :  ${result.code} ${result.message} ${result.data?.status}  ${result.data?.memberId}  ${result.data?.calledAt}")
                    }
                    is Resource.Loading ->{
                        stateUserInfoNickname = stateUserInfoNickname.copy(isLoading = true)
                        Log.e("nicknameedit-viewmodel","로딩중 :  ${result.code} ${result.message}")
                    }
                }
            }
            getUserInfo()
        } catch (e: Exception) {}
    }
    suspend fun patchUserPreferences(){
        try{
            val result = patchUserInfoPreferencesUseCase(
                accessToken = "Bearer " +dataStore.data.first().accessToken.toString(),
                userInfoPreferences = UserInfoPreferencesRequest(
                    stateUserInfoPreferences.preferBeverageList.filterIndexed { index, _ ->
                        stateUserInfoPreferences.preferBeverageCheckList.getOrNull(index) == true
                    }.map { it.categoryName }
                )
            )
            Log.d("preferencesedit-viewmodel", "${"Bearer " +dataStore.data.first().accessToken.toString()}")
            Log.d("preferencesedit-viewmodel", "${UserInfoPreferencesRequest(stateUserInfoPreferences.preferBeverageList.map{it.categoryName})}")

            result.collect{result->
                when(result){
                    is Resource.Success ->{
                        Log.e("preferencesedit-viewmodel","성공 :  ${result.code} ${result.message}")
                    }
                    is Resource.Error ->{
                        stateUserInfoPreferences = stateUserInfoPreferences.copy(error = result.message ?: "An unexpeted error occured")
                        Log.e("preferencesedit-viewmodel","에러 :  ${result.code} ${result.message}")
                    }
                    is Resource.Loading ->{
                        stateUserInfoPreferences = stateUserInfoPreferences.copy(isLoading = false)
                        Log.e("preferencesedit-viewmodel","로딩중 :  ${result.code} ${result.message}")
                    }
                }
            }
            getUserInfo()
        } catch (e: Exception) {}
    }
    suspend fun patchUserInfoProfile(){
        try{
            val result = patchUserInfoProfileUseCase(
                accessToken = "Bearer " +dataStore.data.first().accessToken.toString(),
                userInfoProfile = UserInfoProfileRequest(newProfile = stateUserInfo.profileUrl)
            )
            result.collect { result->
                when(result){
                    is Resource.Success ->{
                        Log.e("nicknameedit-viewmodel","성공 : ${result.data?.status}  ${result.data?.memberId}  ${result.data?.calledAt}")
                    }
                    is Resource.Error ->{
                        stateUserInfo = stateUserInfo.copy(error = result.message ?: "An unexpeted error occured")
                        Log.e("nicknameedit-viewmodel","에러 :  ${result.code} ${result.message}")
                    }
                    is Resource.Loading ->{
                        stateUserInfo = stateUserInfo.copy(isLoading = true)
                        Log.e("nicknameedit-viewmodel","로딩중 :  ${result.code} ${result.message}")
                    }
                }
            }
            getUserInfo()
        } catch (e: Exception) {}
    }

}