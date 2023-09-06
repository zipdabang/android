package com.zipdabang.zipdabang_android.module.drawer.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zipdabang.zipdabang_android.common.Constants
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.drawer.domain.usecase.GetUserInfoUseCase
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.AuthRequest
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.PhoneRequest
import com.zipdabang.zipdabang_android.module.sign_up.domain.usecase.GetNicknameUseCase
import com.zipdabang.zipdabang_android.module.sign_up.domain.usecase.PostAuthUseCase
import com.zipdabang.zipdabang_android.module.sign_up.domain.usecase.PostPhoneSmsUseCase
import com.zipdabang.zipdabang_android.module.sign_up.domain.usecase.ValidateBirthdayUseCase
import com.zipdabang.zipdabang_android.module.sign_up.domain.usecase.ValidateNicknameUseCase
import com.zipdabang.zipdabang_android.module.sign_up.domain.usecase.ValidatePhoneUseCase
import com.zipdabang.zipdabang_android.module.sign_up.ui.viewmodel.NicknameFormState
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
    private val postPhoneSmsUseCase: PostPhoneSmsUseCase,
    private val postAuthUseCase: PostAuthUseCase,
    private val validateBirthdayUseCase : ValidateBirthdayUseCase = ValidateBirthdayUseCase(),
    private val validatePhoneUseCase: ValidatePhoneUseCase = ValidatePhoneUseCase(),
    private val validateNicknameUseCase: ValidateNicknameUseCase = ValidateNicknameUseCase(),
    private val dataStore: DataStore<Token>
) : ViewModel() {

    var stateUserInfo by mutableStateOf(UserInfoState())
    var stateUserInfoBasic by mutableStateOf(UserInfoBasicState())
    var stateUserInfoDetail by mutableStateOf(UserInfoDetailState())
    var stateUserInfoNickname by mutableStateOf(UserInfoNicknameState())
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
        }
    }
    fun updateValidateBirthday() {
        val birthdayResult = validateBirthdayUseCase(stateUserInfoBasic.birthday)
        stateUserInfoBasic = stateUserInfoBasic.copy(birthdayIsTried = true)

        if(birthdayResult.successful){
            stateUserInfoBasic = stateUserInfoBasic.copy(
                birthday = stateUserInfoBasic.birthday,
                //validate = true,
            )
        } else {
            stateUserInfoBasic = stateUserInfoBasic.copy(
                birthday = stateUserInfoBasic.birthday,
                birthdayErrorMessage = birthdayResult.errorMessage,
                birthdayIsError = true,
                //validate = false,
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


    /*UserInfoNicknameScreen*/
    fun onNicknameEvent(event : UserInfoNicknameEvent){
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
                    )
                }
            }
            is UserInfoNicknameEvent.NicknameCliked ->{
                val nicknameResult = validateNicknameUseCase(stateUserInfoNickname.nickname)

                if(nicknameResult.successful){
                    getNickname()
                } else {
                    stateUserInfoNickname = stateUserInfoNickname.copy(
                        isTried = true,
                        isError = true,
                        errorMessage = nicknameResult.errorMessage
                    )
                }
            }
        }
    }


    init{
        viewModelScope.launch {
            getUserInfo()
        }
    }
    private suspend fun getUserInfo(){
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
                        gender =  if(result.data?.memberBasicInfoDto?.genderType == "WOMAN") "여" else "남",
                        phoneNumber = result.data?.memberBasicInfoDto?.phoneNum ?: "",
                        zipcode = result.data?.memberDetailInfoDto?.zipCode ?: "",
                        address = result.data?.memberDetailInfoDto?.address ?: "",
                        detailAddress = result.data?.memberDetailInfoDto?.detailAddress ?: "",
                        nickname = result.data?.nickname ?: "",
                    )
                    stateUserInfoBasic = stateUserInfoBasic.copy(
                        isLoading = false,
                        name = result.data?.memberBasicInfoDto?.name ?: "",
                        birthday = result.data?.memberBasicInfoDto?.birth ?: "",
                        gender =  if(result.data?.memberBasicInfoDto?.genderType == "WOMAN") "여" else "남",
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
                    Log.e("drawer-userinfo-viewmodel", "성공 ${result.code}")
                }
                is Resource.Error ->{
                    stateUserInfo = stateUserInfo.copy(error = result.message ?: "An unexpeted error occured")
                    stateUserInfoBasic = stateUserInfoBasic.copy(error = result.message ?: "An unexpeted error occured")
                    stateUserInfoDetail = stateUserInfoDetail.copy(error = result.message ?: "An unexpeted error occured")
                    stateUserInfoNickname = stateUserInfoNickname.copy(error = result.message ?: "An unexpeted error occured")
                    Log.e("drawer-userinfo-viewmodel", "에러 ${result.code}")
                }
                is Resource.Loading -> {
                    stateUserInfo = stateUserInfo.copy(isLoading = true)
                    stateUserInfoBasic = stateUserInfoBasic.copy(isLoading = true)
                    stateUserInfoDetail = stateUserInfoDetail.copy(isLoading = true)
                    stateUserInfoNickname = stateUserInfoNickname.copy(isLoading = true)
                    Log.e("drawer-userinfo-viewmodel", "로딩중 ${result.code}")
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
                        )
                    } else { //닉네임 불가능, 2052:닉네임 중복, 4019:형식, 4020:욕
                        stateUserInfoNickname = stateUserInfoNickname.copy(
                            nickname = stateUserInfoNickname.nickname,
                            isTried = true,
                            isSuccess = false,
                            isError = true,
                            errorMessage = result.data?.message ?: "",
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
}