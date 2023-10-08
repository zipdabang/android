package com.zipdabang.zipdabang_android.module.drawer.domain.usecase

import android.annotation.SuppressLint
import android.util.Log
import androidx.datastore.core.DataStore
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.drawer.data.remote.reporterror.reportDto
import com.zipdabang.zipdabang_android.module.drawer.domain.repository.DrawerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PostReportUseCase @Inject constructor(
    private val repository : DrawerRepository,
    private val tokenDataStore : DataStore<Token>
) {
    @SuppressLint("SuspiciousIndentation")
    operator fun invoke(
        email: String, title:String,
        content:String, imageList: List<MultipartBody.Part>
    ) : Flow<Resource<reportDto>> = flow{

        try{
        val requestEmail : RequestBody= RequestBody.create("text/plain".toMediaTypeOrNull(),email)
        val requestTitle = RequestBody.create("text/plain".toMediaTypeOrNull(),title)
        val requestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),content)

            emit(Resource.Loading())
            val token = "Bearer ${tokenDataStore.data.first().accessToken}"


            val reportResponse = repository.postErrorReport(
                accessToken = token, email =requestEmail,
                title =requestTitle, body = requestBody,
                imageList =imageList)
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