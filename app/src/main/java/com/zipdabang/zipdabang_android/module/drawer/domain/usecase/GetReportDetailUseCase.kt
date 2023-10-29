package com.zipdabang.zipdabang_android.module.drawer.domain.usecase

import android.util.Log
import androidx.datastore.core.DataStore
import com.zipdabang.zipdabang_android.common.Constants
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.common.ResponseCode
import com.zipdabang.zipdabang_android.common.getErrorCode
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.drawer.data.remote.noticedto.NoticeListDto
import com.zipdabang.zipdabang_android.module.drawer.data.remote.quitdto.QuitDto
import com.zipdabang.zipdabang_android.module.drawer.data.remote.reporterror.detail.ReportDetailDto
import com.zipdabang.zipdabang_android.module.drawer.domain.repository.DrawerRepository
import com.zipdabang.zipdabang_android.module.my.domain.repository.MyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetReportDetailUseCase @Inject constructor(
    private val repository: DrawerRepository,
    private val dataStore: DataStore<Token>
){

    operator fun invoke(reportId : Int) : Flow<Resource<ReportDetailDto>> = flow{
        try {
            emit(Resource.Loading())
            val accessToken = dataStore.data.first().accessToken ?: Constants.TOKEN_NULL
            val token = "Bearer " + accessToken
            val response = repository.getReportDetail(accessToken =token, inqueryId = reportId)
            emit(
                Resource.Success(
                    data = response,
                    code = response.code,
                    message = response.message
                )
            )
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()
            val errorCode = errorBody?.getErrorCode()
            errorCode?.let {
                emit(
                    Resource.Error(
                        message = ResponseCode.getMessageByCode(errorCode),
                        code = errorCode
                    )
                )
                return@flow
            }
            emit(Resource.Error(message = e.message ?: "unexpected http error"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}
