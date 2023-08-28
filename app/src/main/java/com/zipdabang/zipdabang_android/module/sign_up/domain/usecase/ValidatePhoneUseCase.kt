package com.zipdabang.zipdabang_android.module.sign_up.domain.usecase

import android.content.Context
import android.util.Patterns
import androidx.compose.ui.res.stringResource
import com.zipdabang.zipdabang_android.R
import java.util.regex.Pattern
import javax.inject.Inject

class ValidatePhoneUseCase @Inject constructor(

){
    operator fun invoke(phonenumber : String) : ValidationResult{
        //phonenumber 형식에 맞지 않는 경우
        val phonenumberPattern = Patterns.PHONE
        if(phonenumberPattern.matcher(phonenumber).matches() && phonenumber.length == 11){
            return ValidationResult(
                successful = true
            )
        }else{
            return ValidationResult(
                successful = false,
                errorMessage ="전화번호를 정확히 입력해주세요"
            )
        }
    }
}