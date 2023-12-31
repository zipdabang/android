package com.zipdabang.zipdabang_android.module.drawer.data.remote

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
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface DrawerApi {
    // 회원정보 불러오기
    @GET("myInfo")
    suspend fun getUserInfo(
        @Header("Authorization") accessToken: String,
    ) : UserInfoResponse

    // 프로필 수정하기
    @Multipart
    @PATCH("myInfo/profileImage")
    suspend fun patchUserInfoProfile(
        @Header("Authorization") accessToken: String,
        @Part userInfoProfile : MultipartBody.Part
    ) : UserInfoEditResponse

    // 기본프로필로 수정하기
    @PATCH("members/defaultProfile")
    suspend fun patchUserInfoDefaultProfile(
        @Header("Authorization") accessToken: String,
    ) : UserInfoEditResponse

    // 기본 정보 수정
    @PATCH("myInfo/basicInfo")
    suspend fun patchUserInfoBasic(
        @Header("Authorization") accessToken: String,
        @Body userInfoBasic : UserInfoBasicRequest
    ) : UserInfoEditResponse
    // 전화번호 인증
    @POST("members/phone/sms")
    suspend fun postPhoneSms(
        @Body phoneRequest : PhoneRequest
    ) : AuthResponse
    // 인증번호 인증
    @POST("members/phone/auth")
    suspend fun postPhoneAuth(
        @Body authRequest : AuthRequest
    ) : AuthResponse


    // 상세 정보 수정
    @PATCH("myInfo/detailInfo")
    suspend fun patchUserInfoDetail(
        @Header("Authorization") accessToken: String,
        @Body userInfoDetail : UserInfoDetailRequest
    ) : UserInfoEditResponse

    // 한줄 소개 수정
    @PATCH("members/caption")
    suspend fun patchUserInfoOneLine(
        @Header("Authorization") accessToken: String,
        @Query("caption") caption : String
    ): UserInfoEditResponse

    // 닉네임 수정
    @PATCH("myInfo/nickname")
    suspend fun patchUserInfoNickname(
        @Header("Authorization") accessToken: String,
        @Body userInfoNickname : UserInfoNicknameRequest
    ) : UserInfoEditResponse
    // 닉네임 중복 확인
    @GET("members/exist-nickname")
    suspend fun getNickname(
        @Query("nickname") nickname : String
    ) : NicknameResponse

    // 선호 음료 수정
    @PATCH("myInfo/category")
    suspend fun patchUserPreferences(
        @Header("Authorization") accessToken: String,
        @Body userInfoPreferences : UserInfoPreferencesRequest
    ) : UserInfoEditResponse

    @PATCH("members/deregister")
    suspend fun patchQuit(
        @Header("Authorization") accessToken: String,
        @Query("deregisterTypes") deregisterTypes: List<String>,
        @Query("feedback") feedback: String,
    ) :QuitDto

    @Multipart
    @POST("members/inquiries")
    suspend fun postErrorReport(
        @Header("Authorization") accessToken: String,
        @Part("email") email: RequestBody,
        @Part("title") title: RequestBody,
        @Part("body") body: RequestBody,
        @Part imageList: List<MultipartBody.Part>
    ) : reportDto


    @GET("members/inquiries")
    suspend fun getErrorList(
        @Header("Authorization") accessToken: String,
        @Query("page") page : Int
    ) : ReportListDto

    @GET("members/inquiries/{inqueryId}")
    suspend fun getReportDetail(
        @Header("Authorization") accessToken: String,
        @Path(value = "inqueryId") reportId : Int
    ) : ReportDetailDto

    @GET("notices")
    suspend fun getNoticeList(
        @Header("Authorization") accessToken: String
    ) : NoticeListDto

}