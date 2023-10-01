package com.zipdabang.zipdabang_android.module.drawer.domain.usecase

import androidx.datastore.core.DataStore
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.drawer.data.remote.reporterror.reportDto
import com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfodto.UserInfoEditResult
import com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfodto.UserInfoProfileRequest
import com.zipdabang.zipdabang_android.module.drawer.domain.repository.DrawerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PostReportUseCase @Inject constructor(
    private val repository : DrawerRepository,
    private val tokenDataStore : DataStore<Token>
) {
    operator fun invoke(email: RequestBody, title:RequestBody,content:RequestBody, imageList: List<MultipartBody.Part>) : Flow<Resource<reportDto>> = flow{
        try{
            emit(Resource.Loading())
            val token = "Bearer ${tokenDataStore.data.first().accessToken}"
            val reportResponse = repository.postErrorReport(accessToken= token, email=email,title = title, body = content,imageList=imageList)
            emit(
                Resource.Success(
                data = reportResponse,
                code = reportResponse.code,
                message = reportResponse.message
            )
            )
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}