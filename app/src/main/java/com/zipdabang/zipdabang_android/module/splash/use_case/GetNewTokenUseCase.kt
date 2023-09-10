package com.zipdabang.zipdabang_android.module.splash.use_case

import androidx.datastore.core.DataStore
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.splash.data.AutoLoginRepositoryImpl
import com.zipdabang.zipdabang_android.module.splash.data.NewTokenDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetNewTokenUseCase @Inject constructor(
    private val autoLoginRepository: AutoLoginRepositoryImpl,
    private val tokenDataStore: DataStore<Token>
) {
    operator fun invoke(): Flow<Resource<NewTokenDto>> = flow {
        try {
            emit(Resource.Loading())

            val refreshToken = tokenDataStore.data.first().refreshToken
            val result = autoLoginRepository.getNewAccessToken(refreshToken = refreshToken)

            result?.let {
                emit(Resource.Success(
                    data = it,
                    code = it.code,
                    message = it.message
                ))
            } ?: run {
                emit(Resource.Error(message = "nothing derived from network"))
            }

        } catch (e: HttpException) {
            emit(Resource.Error(message = e.message ?: "unexpected http error"))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.message ?: "unexpected io error"))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: "unexpected etc error"))
        }
    }
}