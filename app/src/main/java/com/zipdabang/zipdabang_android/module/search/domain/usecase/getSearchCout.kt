package com.zipdabang.zipdabang_android.module.search.domain.usecase

import android.util.Log
import androidx.datastore.core.DataStore
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.common.SearchResource
import com.zipdabang.zipdabang_android.common.getErrorCode
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.search.data.dto.recipecategory.SearchCountDto
import com.zipdabang.zipdabang_android.module.search.data.dto.searchpreview.SearchDto
import com.zipdabang.zipdabang_android.module.search.domain.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class GetSearchCountUseCase @Inject constructor(
    private val repository: SearchRepository,
    private val tokenDataStore : DataStore<Token>
) {

    operator fun invoke(cateogryId: Int,keyword: String) : Flow<Resource<SearchCountDto>> = flow {
        try{
            emit(Resource.Loading())
            val accessToken = ("Bearer " + tokenDataStore.data.first().accessToken)
            Log.e("search token",accessToken)
            Log.e("search keyword",keyword)

            val data = repository.getSearchCount(accessToken,cateogryId,keyword)
            emit(Resource.Success(data =data ,code =data.code, message = data.message))
        } catch (e : HttpException){
            val errorBody = e.response()?.errorBody()
            val errorCode = errorBody?.getErrorCode()
            errorCode?.let {
                emit(
                    Resource.Error(
                        message = e.response()?.errorBody().toString(),
                        code = errorCode
                    )
                )
                return@flow
            }
            emit(Resource.Error(message = e.message ?: "unexpected http error"))
        } catch(e : IOException){
            emit(Resource.Error("Couldn't reach server. Check your internal code"))
        }

    }

}