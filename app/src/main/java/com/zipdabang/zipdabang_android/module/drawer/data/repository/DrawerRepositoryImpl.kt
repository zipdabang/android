package com.zipdabang.zipdabang_android.module.drawer.data.repository

import com.zipdabang.zipdabang_android.module.drawer.data.remote.DrawerApi
import com.zipdabang.zipdabang_android.module.drawer.data.remote.UserInfoResponse
import com.zipdabang.zipdabang_android.module.drawer.domain.repository.DrawerRepository
import javax.inject.Inject

class DrawerRepositoryImpl @Inject constructor(
    private val api : DrawerApi
) : DrawerRepository{
    override suspend fun getUserInfo(accessToken : String): UserInfoResponse {
        return api.getUserInfo(accessToken = accessToken)
    }

}