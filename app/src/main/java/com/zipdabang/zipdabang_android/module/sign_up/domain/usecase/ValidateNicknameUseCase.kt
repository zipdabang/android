package com.zipdabang.zipdabang_android.module.sign_up.domain.usecase

import javax.inject.Inject

class ValidateNicknameUseCase @Inject constructor(

){
    operator fun invoke(nickname : String) : ValidationResult{
        if(nickname.length < 2 || nickname.length > 6){
            return ValidationResult(
                successful = false,
                errorMessage = "닉네임은 2-6자로 구성해주세요"
            )
        }else{
            return ValidationResult(
                successful = true
            )
        }
    }
}