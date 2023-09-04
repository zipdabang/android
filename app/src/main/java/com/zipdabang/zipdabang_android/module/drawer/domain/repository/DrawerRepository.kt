package com.zipdabang.zipdabang_android.module.drawer.domain.repository

import com.zipdabang.zipdabang_android.module.drawer.data.remote.UserInfoResponse

interface DrawerRepository {
    suspend fun getUserInfo(accessToken : String) : UserInfoResponse
}