package com.zipdabang.zipdabang_android.module.drawer.ui.quit

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.drawer.ui.viewmodel.QuitViewModel
import com.zipdabang.zipdabang_android.ui.component.AppBarSignUp
import com.zipdabang.zipdabang_android.ui.component.CustomDialogOnlyConfirm
import com.zipdabang.zipdabang_android.ui.component.CustomDialogQuitConfirm
import com.zipdabang.zipdabang_android.ui.component.GeneralDialogType1
import com.zipdabang.zipdabang_android.ui.component.MainAndSubTitle
import com.zipdabang.zipdabang_android.ui.component.PrimaryButtonWithStatus
import com.zipdabang.zipdabang_android.ui.component.TextFieldForDrawerMultiline
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuitScreen(
    onQuitClick : ()-> Unit,
    quitViewModel: QuitViewModel = hiltViewModel()
){

    var checkReasonList  = mutableListOf<String>()
    var ButtonFilled by remember {
        mutableStateOf(false)
    }
    var text by remember {
        mutableStateOf("")
    }




    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            AppBarSignUp(
                navigationIcon = R.drawable.ic_topbar_backbtn,
                onClickNavIcon = {  },
                centerText = stringResource(id = R.string.drawer_quit)
            )
        }
    ) {
        val scrollState= rememberScrollState()
        Column(modifier = Modifier
            .padding(top = it.calculateTopPadding(), start = 16.dp, end = 16.dp)
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(color = Color.White)

        ){
//            val reasonList = listOf<String?>(
//                null,
//                "사고싶은 물품이 없어요.",
//                "앱을 이용하지 않아요.",
//                "앱 이용이 불편해요.",
//                "새 계정을 만들고 싶어요.",
//                "비매너 유저를 만났어요.",
//                "기타"
//            )
            val reasonList = QuitReasons.values()
            MainAndSubTitle(
                mainValue = "집다방 탈퇴",
                mainTextStyle = ZipdabangandroidTheme.Typography.twentytwo_700,
                mainTextColor = ZipdabangandroidTheme.Colors.Typo,
                subValue = "회원님의 집다방 탈퇴 이유를 알려주세요",
                subTextStyle = ZipdabangandroidTheme.Typography.sixteen_300,
                subTextColor = ZipdabangandroidTheme.Colors.Typo
            )



            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()

            ){
                reasonList.forEachIndexed { index, reasonData ->
                    Log.e("quit_log_inex",index.toString())

                    if (reasonData.reason != null) {
                        QuitCheckBoxComponent(
                            reason = reasonData.reason ,
                            ischeckedChange = {
                                checkReasonList.add(reasonData.requestString)
                                ButtonFilled = checkReasonList.isNotEmpty()
                            },
                            isNotChecked = {
                                checkReasonList.remove(reasonData.requestString)
                                ButtonFilled = checkReasonList.isNotEmpty()
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text("집다방이 개선해야 할 점에 대해 알려주세요",
                style = ZipdabangandroidTheme.Typography.sixteen_500)
            Spacer(modifier = Modifier.height(10.dp))


            TextFieldForDrawerMultiline(
                value = text,
                onValueChanged = {
                        newText, maxLength ->
                    if(newText.length <= maxLength){
                        text = newText
                    }
                },
                placeholderValue = "집다방이 개선해야 할 점에 대해 알려주세요",
                height = 100.dp,
                maxLines = 3,
                maxLength = 250,
                isError = false,
                imeAction = ImeAction.Default
            )

            Spacer(modifier = Modifier.height(133.dp))

            val showDialogSave = remember { mutableStateOf(false) }

            if (showDialogSave.value) {
                CustomDialogQuitConfirm(
                    title = "집다방 탈퇴 완료",
                    text = "지금까지 집다방을 이용해주셔서 감사합니다. 다음에도 좋은 인연으로 만날 수 있길 바랍니다. 건강하고 행복하세요!",
                    acceptText = "확인",
                    onAcceptClick = {
                        showDialogSave.value = false
                        onQuitClick()
                    }
                )
            }

            Log.e("quit_log", checkReasonList.size.toString())
            PrimaryButtonWithStatus(
                text = "제출하기",
                onClick = {
                    quitViewModel.pathQuitReasonUseCase(
                        checkReasonList, text , onDialogShow = {
                            showDialogSave.value = true
                        }
                    )
                          },
                isFormFilled = ButtonFilled
            )


        }
    }



    }

@Preview
@Composable
fun previewQuitScreen(){
 //   QuitScreen()
}