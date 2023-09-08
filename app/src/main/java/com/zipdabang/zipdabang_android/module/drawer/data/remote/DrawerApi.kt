package com.zipdabang.zipdabang_android.module.drawer.data.remote

import com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfodto.UserInfoBasicRequest
import com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfodto.UserInfoDetailRequest
import com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfodto.UserInfoEditResponse
import com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfodto.UserInfoNicknameRequest
import com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfodto.UserInfoProfileRequest
import com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfodto.UserInfoResponse
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.AuthRequest
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.AuthResponse
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.NicknameResponse
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.PhoneRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface DrawerApi {
    // 회원정보 불러오기
    @GET("myInfo")
    suspend fun getUserInfo(
        @Header("Authorization") accessToken: String,
    ) : UserInfoResponse

    // 회원정보 수정하기
    @PATCH("myInfo/profileImage")
    suspend fun patchUserInfoProfile(
        @Header("Authorization") accessToken: String,
        @Body userInfoProfile : UserInfoProfileRequest
    ) : UserInfoEditResponse


    @PATCH("myInfo/basicInfo")
    suspend fun patchUserInfoBasic(
        @Header("Authorization") accessToken: String,
        @Body userInfoBasic : UserInfoBasicRequest
    ) : UserInfoEditResponse
    @POST("members/phone/sms")
    suspend fun postPhoneSms(
        @Body phoneRequest : PhoneRequest
    ) : AuthResponse
    @POST("members/phone/auth")
    suspend fun postPhoneAuth(
        @Body authRequest : AuthRequest
    ) : AuthResponse


    @PATCH("myInfo/detailInfo")
    suspend fun patchUserInfoDetail(
        @Header("Authorization") accessToken: String,
        @Body userInfoDetail : UserInfoDetailRequest
    ) : UserInfoEditResponse


    @PATCH("myInfo/nickname")
    suspend fun patchUserInfoNickname(
        @Header("Authorization") accessToken: String,
        @Body userInfoNickname : UserInfoNicknameRequest
    ) : UserInfoEditResponse
    @GET("members/exist-nickname")
    suspend fun getNickname(
        @Query("nickname") nickname : String
    ) : NicknameResponse
}