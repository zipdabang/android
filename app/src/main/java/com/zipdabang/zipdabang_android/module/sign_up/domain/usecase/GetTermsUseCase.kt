package com.zipdabang.zipdabang_android.module.sign_up.domain.usecase

import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.TermsResult
import com.zipdabang.zipdabang_android.module.sign_up.domain.repository.SignUpRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetTermsUseCase @Inject constructor(
    private val repository : SignUpRepository
) {
    operator fun invoke() : Flow<Resource<TermsResult>> = flow {
        try{
            emit(Resource.Loading())
            val terms = repository.getTerms()
            emit(Resource.Success(
                data = terms.result,
                code = terms.code,
                message= terms.message,
            ))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}