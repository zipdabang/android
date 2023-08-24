package com.zipdabang.zipdabang_android.module.sign_up.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.core.navigation.AuthScreen
import com.zipdabang.zipdabang_android.module.sign_up.ui.component.CheckBoxWithText
import com.zipdabang.zipdabang_android.module.sign_up.ui.component.CheckBoxWithTextAndButton
import com.zipdabang.zipdabang_android.module.sign_up.ui.viewmodel.AuthSharedViewModel
import com.zipdabang.zipdabang_android.ui.component.AppBarSignUp
import com.zipdabang.zipdabang_android.ui.component.MainAndSubTitle
import com.zipdabang.zipdabang_android.ui.component.PrimaryButtonWithStatus
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun TermsScreen(
    navController: NavHostController,
    authSharedViewModel: AuthSharedViewModel= hiltViewModel(),
    onClickNext: ()->Unit,
    onClickDetailNext : (Int) -> Unit,
) {
    val stateTerms = authSharedViewModel.stateTerms.value
    val stateTermsAllagree by authSharedViewModel.stateTermsAllagree.collectAsState()
    val stateTermsListAgree by authSharedViewModel.stateTermsListAgree.collectAsState()
    val stateTermsValidate by authSharedViewModel.stateTermsValidate.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            AppBarSignUp(
                navigationIcon = R.drawable.ic_topbar_backbtn,
                onClickNavIcon = {},
                centerText = stringResource(id = R.string.signup)
            )
        },
    ) {
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            color = Color.White
        ){
            Column(
                modifier = Modifier
                    .padding(16.dp, 10.dp, 16.dp, 0.dp)
                    .background(Color.White)
                    .fillMaxSize(),
            ){
                MainAndSubTitle(
                    mainValue = stringResource(id = R.string.signup_terms_maintitle),
                    mainTextStyle = ZipdabangandroidTheme.Typography.twentytwo_700,
                    mainTextColor = ZipdabangandroidTheme.Colors.Typo,
                    subValue = stringResource(id = R.string.signup_terms_subtitle),
                    subTextStyle = ZipdabangandroidTheme.Typography.sixteen_300,
                    subTextColor =  ZipdabangandroidTheme.Colors.Typo
                )

                Spacer(modifier = Modifier.height(40.dp))

                CheckBoxWithText(
                    isCheckBox = true,
                    isChecked = stateTermsAllagree,
                    isCheckedChange = {selectedChecked ->
                        authSharedViewModel.updateTermsAllagree(selectedChecked)
                    },
                    mainValue = stringResource(id = R.string.signup_terms_allagree),
                    mainTextStyle = ZipdabangandroidTheme.Typography.sixteen_700,
                    isDetailValue = true,
                    detailValue = stringResource(id = R.string.signup_terms_allagreedetail),
                    detailTextStyle = ZipdabangandroidTheme.Typography.twelve_300
                )

                 Divider(
                     modifier = Modifier
                         .fillMaxWidth()
                         .padding(vertical = 20.dp, horizontal = 0.dp),
                     color = Color(0xFFCDC6C3)
                 )

                CheckBoxWithText(
                    isCheckBox = false,
                    isChecked = null,
                    isCheckedChange = {},
                    mainValue = stringResource(id = R.string.signup_terms_zipdabangserviceagree),
                    mainTextStyle = ZipdabangandroidTheme.Typography.sixteen_700,
                    isDetailValue = false,
                    detailValue = null,
                    detailTextStyle = null
                )

                LazyColumn(
                    modifier = Modifier.padding(0.dp,12.dp,0.dp,0.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ){
                    itemsIndexed(stateTerms.termsList) { index, termInfo ->
                        val termInfo = stateTerms.termsList[index]

                        if(termInfo.isMoreToSee){
                            CheckBoxWithTextAndButton(
                                isChecked = stateTermsListAgree[index],
                                isCheckedChange = { selectedChecked ->
                                    authSharedViewModel.updateTermsListAgree(index, selectedChecked)
                                },
                                mainValue = termInfo.termsTitle,
                                mainTextStyle = ZipdabangandroidTheme.Typography.fourteen_500,
                                onClick = {
                                    onClickDetailNext(index)
                                }
                            )
                        } else{
                            CheckBoxWithText(
                                isCheckBox = true,
                                isChecked = stateTermsListAgree[index],
                                isCheckedChange = {selectedChecked ->
                                    authSharedViewModel.updateTermsListAgree(index, selectedChecked)
                                },
                                mainValue = termInfo.termsTitle,
                                mainTextStyle = ZipdabangandroidTheme.Typography.fourteen_500,
                                isDetailValue = true,
                                detailValue = termInfo.termsBody,
                                detailTextStyle = ZipdabangandroidTheme.Typography.twelve_300,)
                        }
                    }
                }
                if(stateTerms.error.isNotBlank()){
                    Text(
                        text = stateTerms.error,
                        color = Color.Red,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
                if(stateTerms.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }
            }
            //하단 버튼
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier.padding(16.dp,0.dp,16.dp, 12.dp)
            ){
                PrimaryButtonWithStatus(
                    text= stringResource(id = R.string.signup_btn_termsagree),
                    onClick={ onClickNext() },
                    isFormFilled = stateTermsValidate
                )
            }
        }
    }
    
}


@Preview
@Composable
fun PreviewTermsScreen(){
    val navController = rememberNavController()
    TermsScreen(
        navController = navController,
        onClickNext = {
            navController.navigate(AuthScreen.RegisterUserInfo.route)
        },
        onClickDetailNext = { }
    )
}