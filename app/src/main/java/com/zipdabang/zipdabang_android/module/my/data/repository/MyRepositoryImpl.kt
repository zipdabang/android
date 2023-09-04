package com.zipdabang.zipdabang_android.module.my.data.repository

import com.zipdabang.zipdabang_android.module.my.data.remote.MyApi
import com.zipdabang.zipdabang_android.module.my.domain.repository.MyRepository
import javax.inject.Inject

class MyRepositoryImpl @Inject constructor(
    private val api : MyApi
) : MyRepository{

}