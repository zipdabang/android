package com.zipdabang.zipdabang_android.module.drawer.domain.repository

import com.zipdabang.zipdabang_android.module.drawer.data.remote.noticedto.NoticeListDto
import com.zipdabang.zipdabang_android.module.drawer.data.remote.quitdto.QuitDto
import com.zipdabang.zipdabang_android.module.drawer.data.remote.reporterror.ReportListDto
import com.zipdabang.zipdabang_android.module.drawer.data.remote.reporterror.detail.ReportDetailDto
import com.zipdabang.zipdabang_android.module.drawer.data.remote.reporterror.reportDto
import com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfo.UserInfoBasicRequest
import com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfo.UserInfoDetailRequest
import com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfo.UserInfoEditResponse
import com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfo.UserInfoNicknameRequest
import com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfo.UserInfoPreferencesRequest
import com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfo.UserInfoResponse
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
    suspend fun patchUserInfoProfile(accessToken : String, userInfoProfile : MultipartBody.Part) : UserInfoEditResponse
    suspend fun patchUserInfoDefaultProfile(accessToken : String) : UserInfoEditResponse
    suspend fun patchUserInfoBasic(accessToken: String, userInfoBasic : UserInfoBasicRequest): UserInfoEditResponse
    suspend fun patchUserInfoDetail(accessToken: String, userInfoDetail : UserInfoDetailRequest): UserInfoEditResponse
    suspend fun patchUserInfoNickname(accessToken: String, userInfoNickname : UserInfoNicknameRequest): UserInfoEditResponse
    suspend fun patchUserInfoOneLine(accessToken: String, oneline:String) : UserInfoEditResponse
    suspend fun patchQuit(accessToken: String, deregisterTypes: List<String>, feedback : String): QuitDto
    suspend fun postErrorReport(accessToken: String, email: RequestBody, title: RequestBody, body: RequestBody, imageList: List<MultipartBody.Part>) : reportDto
    suspend fun patchUserPreferences(accessToken: String, userInfoPreferences : UserInfoPreferencesRequest ) : UserInfoEditResponse
    suspend fun getReportList(accessToken: String, page: Int) : ReportListDto
    suspend fun getNoticeList(accessToken: String) : NoticeListDto
    suspend fun getReportDetail(accessToken: String, inqueryId : Int) : ReportDetailDto

}