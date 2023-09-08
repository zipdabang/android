package com.zipdabang.zipdabang_android.module.sign_up.domain.usecase

import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.module.drawer.domain.repository.DrawerRepository
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.NicknameResponse
import com.zipdabang.zipdabang_android.module.sign_up.domain.repository.SignUpRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetNicknameUseCase @Inject constructor(
    private val repository : SignUpRepository,
    private val repositoryDrawer : DrawerRepository
) {
    operator fun invoke(nickname : String) : Flow<Resource<NicknameResponse>> = flow{
        try{
            emit(Resource.Loading())
            val nicknameResponse = repository.getNickname(nickname = nickname)
            val nicknameResponseDrawer = repositoryDrawer.getNickname(nickname = nickname)
            emit(
                Resource.Success(
                    data = nicknameResponse,
                    code = nicknameResponse.code,
                    message = nicknameResponse.message
                )
            )
            emit(
                Resource.Success(
                    data = nicknameResponseDrawer,
                    code = nicknameResponseDrawer.code,
                    message = nicknameResponseDrawer.message
                )
            )
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}