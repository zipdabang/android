package com.zipdabang.zipdabang_android.module.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zipdabang.zipdabang_android.ui.component.CheckBoxCustom
import com.zipdabang.zipdabang_android.ui.component.MainAndSubTitle
import com.zipdabang.zipdabang_android.ui.component.RoundedButton
import com.zipdabang.zipdabang_android.ui.component.TextFieldBasic
import com.zipdabang.zipdabang_android.ui.component.TextFieldForContent
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZipdabangandroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(ZipdabangandroidTheme.Shapes.smallRoundedTop),
                ) {
                    Column(){
                        Greeting("집다방","홈카페를 위한 모든 것이 여기에!")
                        var textState = remember { mutableStateOf("") }
                        TextFieldBasic(textState,
                            "ㅁㄴㅇㄹ",
                            "이름",
                            "이름",
                            "회원정보가 잘못됐습니다",
                            "맞습니다",
                            KeyboardType.Text,
                            ImeAction.Done)
                        var textStateTwo = remember { mutableStateOf("") }
                        TextFieldForContent(textStateTwo,
                            false,
                            7,
                            200.dp,
                            "레시피 제목 (최대 5자)",
                            ImeAction.Default,
                            5)
                        RoundedButton(roundedImage = { /*TODO*/ }, buttonText = "생과일 음료")
                        MainAndSubTitle(
                            mainValue = "집다방 서비스 이용 약관",
                            mainTextStyle = ZipdabangandroidTheme.Typography.twentytwo_700,
                            mainTextColor = ZipdabangandroidTheme.Colors.Typo,
                            subValue = "서비스 이용 약관을 확인해주세요",
                            subTextStyle= ZipdabangandroidTheme.Typography.sixteen_500,
                            subTextColor = ZipdabangandroidTheme.Colors.Typo,
                        )
                        Box(modifier = Modifier.align(Alignment.CenterHorizontally)){
                            Column(){
                                CheckBoxCustom(false)
                                CheckBoxCustom(true)
                                /*ImageWithIconAndText()
                                Spacer(modifier = Modifier.height(10.dp))
                                ImageWithIcon()*/
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, name2: String, modifier: Modifier = Modifier) {
    Column(){
        Text(
            text = "$name",
            modifier = Modifier.background(ZipdabangandroidTheme.Colors.Cream),
            style = ZipdabangandroidTheme.Typography.logo,
            color = ZipdabangandroidTheme.Colors.Choco,)
        Text(
            text = "$name2",
            modifier = Modifier.background(ZipdabangandroidTheme.Colors.Cream),
            style = ZipdabangandroidTheme.Typography.underLogo,
            color = ZipdabangandroidTheme.Colors.Choco,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ZipdabangandroidTheme {
        Greeting("집다방","홈카페를 위한 모든 것이 여기에!")
    }
}