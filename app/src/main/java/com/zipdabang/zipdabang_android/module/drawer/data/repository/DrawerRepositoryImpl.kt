package com.zipdabang.zipdabang_android.module.drawer.data.repository

import com.zipdabang.zipdabang_android.module.drawer.data.remote.DrawerApi
import com.zipdabang.zipdabang_android.module.drawer.data.remote.quitdto.QuitDto
import com.zipdabang.zipdabang_android.module.drawer.data.remote.quitdto.QuitRequest
import com.zipdabang.zipdabang_android.module.drawer.data.remote.reporterror.ReportListDto
import com.zipdabang.zipdabang_android.module.drawer.data.remote.reporterror.reportDto
import com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfodto.UserInfoBasicRequest
import com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfodto.UserInfoDetailRequest
import com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfodto.UserInfoEditResponse
import com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfodto.UserInfoNicknameRequest
import com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfodto.UserInfoProfileRequest
import com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfodto.UserInfoResponse
import com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfodto.UserInfoPreferencesRequest
import com.zipdabang.zipdabang_android.module.drawer.domain.repository.DrawerRepository
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.AuthRequest
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.AuthResponse
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.NicknameResponse
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.PhoneRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class DrawerRepositoryImpl @Inject constructor(
    private val api : DrawerApi
) : DrawerRepository{
    override suspend fun getUserInfo(accessToken : String): UserInfoResponse {
        return api.getUserInfo(accessToken = accessToken)
    }

    override suspend fun getNickname(nickname: String): NicknameResponse {
        return api.getNickname(nickname = nickname)
    }

    override suspend fun postPhoneSms(phoneRequest: PhoneRequest): AuthResponse {
        return api.postPhoneSms(phoneRequest = phoneRequest)
    }

    override suspend fun postPhoneAuth(authRequest: AuthRequest): AuthResponse {
       return api.postPhoneAuth(authRequest = authRequest)
    }

    override suspend fun patchUserInfoProfile(
        accessToken: String,
        userInfoProfile: UserInfoProfileRequest
    ): UserInfoEditResponse {
        return api.patchUserInfoProfile(accessToken = accessToken, userInfoProfile =userInfoProfile)
    }

    override suspend fun patchUserInfoBasic(
        accessToken: String,
        userInfoBasic: UserInfoBasicRequest
    ): UserInfoEditResponse {
        return api.patchUserInfoBasic(accessToken = accessToken, userInfoBasic =userInfoBasic)
    }

    override suspend fun patchUserInfoDetail(
        accessToken: String,
        userInfoDetail: UserInfoDetailRequest
    ): UserInfoEditResponse {
        return api.patchUserInfoDetail(accessToken = accessToken, userInfoDetail = userInfoDetail)
    }

    override suspend fun patchUserInfoNickname(
        accessToken: String,
        userInfoNickname: UserInfoNicknameRequest
    ): UserInfoEditResponse {
        return api.patchUserInfoNickname(accessToken = accessToken, userInfoNickname = userInfoNickname)
    }

    override suspend fun patchQuit(
        accessToken: String,
        deregisterTypes: List<String>,
        feedback : String
    ): QuitDto {
        return api.patchQuit(
            accessToken = accessToken,
            deregisterTypes = deregisterTypes,
            feedback = feedback
        )
    }

    override suspend fun postErrorReport(
        accessToken: String,
        email: RequestBody,
        title: RequestBody,
        body: RequestBody,
        imageList: List<MultipartBody.Part>
    ): reportDto {
        return api.postErrorReport(accessToken = accessToken, email = email, title = title, body = body, imageList = imageList )
    }


    override suspend fun patchUserPreferences(
        accessToken: String,
        userInfoPreferences: UserInfoPreferencesRequest
    ): UserInfoEditResponse {
        return api.patchUserPreferences(accessToken = accessToken, userInfoPreferences = userInfoPreferences)
    }

    override suspend fun getReportList(accessToken: String, page: Int): ReportListDto {
        return api.getErrorList(accessToken = accessToken, page = page )
    }

}