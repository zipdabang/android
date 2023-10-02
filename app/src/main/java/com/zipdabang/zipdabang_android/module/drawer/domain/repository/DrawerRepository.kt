package com.zipdabang.zipdabang_android.module.drawer.domain.repository

import com.zipdabang.zipdabang_android.module.drawer.data.remote.quitdto.QuitDto
import com.zipdabang.zipdabang_android.module.drawer.data.remote.quitdto.QuitRequest
import com.zipdabang.zipdabang_android.module.drawer.data.remote.reporterror.reportDto
import com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfodto.UserInfoBasicRequest
import com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfodto.UserInfoDetailRequest
import com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfodto.UserInfoEditResponse
import com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfodto.UserInfoNicknameRequest
import com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfodto.UserInfoProfileRequest
import com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfodto.UserInfoResponse
import com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfodto.UserInfoPreferencesRequest
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.AuthRequest
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.AuthResponse
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.NicknameResponse
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.PhoneRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface DrawerRepository {
    suspend fun getUserInfo(accessToken : String) : UserInfoResponse
    suspend fun getNickname(nickname : String) : NicknameResponse
    suspend fun postPhoneSms(phoneRequest: PhoneRequest) : AuthResponse
    suspend fun postPhoneAuth(authRequest : AuthRequest) : AuthResponse
    suspend fun patchUserInfoProfile(accessToken : String, userInfoProfile : UserInfoProfileRequest) : UserInfoEditResponse
    suspend fun patchUserInfoBasic(accessToken: String, userInfoBasic : UserInfoBasicRequest): UserInfoEditResponse
    suspend fun patchUserInfoDetail(accessToken: String, userInfoDetail : UserInfoDetailRequest): UserInfoEditResponse
    suspend fun patchUserInfoNickname(accessToken: String, userInfoNickname : UserInfoNicknameRequest): UserInfoEditResponse
    suspend fun patchQuit(accessToken: String, deregisterTypes: List<String>, feedback : String): QuitDto
    suspend fun postErrorReport(accessToken: String, email: RequestBody, title: RequestBody, body: RequestBody, imageList: List<MultipartBody.Part>) : reportDto
    suspend fun patchUserPreferences(accessToken: String, userInfoPreferences : UserInfoPreferencesRequest ) : UserInfoEditResponse
}