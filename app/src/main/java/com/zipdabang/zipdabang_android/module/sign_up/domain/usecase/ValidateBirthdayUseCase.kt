package com.zipdabang.zipdabang_android.module.sign_up.domain.usecase

import android.util.Patterns
import com.zipdabang.zipdabang_android.R
import java.util.Calendar
import java.util.regex.Pattern
import javax.inject.Inject

class ValidateBirthdayUseCase @Inject constructor(

){
    operator fun invoke(birthday : String) : ValidationResult{
        //birthday가 6자리 미만인 경우
        if(birthday.length < 6){
            return ValidationResult(
                successful = false,
                errorMessage = R.string.signup_userinfo_birthday_notsix.toString()
            )
        }

        //birthday 형식에 맞지 않는 경우
        //좀 더 수정해야하나
        val birthdayPattern = "^\\d{6}$".toRegex()
        if(!birthdayPattern.matches(birthday.toString())){
            return ValidationResult(
                successful = false,
                errorMessage = R.string.signup_userinfo_birthday_notformed.toString()
            )
        }

        //15세 미만일 경우
        val currentDate = Calendar.getInstance()
        val birthYear = birthday.substring(0, 4).toInt()
        val birthMonth = birthday.substring(4, 6).toInt()
        val age = currentDate.get(Calendar.YEAR) - birthYear -
                if ( currentDate.get(Calendar.MONTH) + 1 < birthMonth ||
                    (currentDate.get(Calendar.MONTH) + 1 == birthMonth && currentDate.get(Calendar.DAY_OF_MONTH) < birthday.toInt() % 100)
                ) 1 else 0

        if (age < 15) {
            return ValidationResult(
                successful = false,
                errorMessage = R.string.signup_userinfo_birthday_underfifteen.toString()
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}