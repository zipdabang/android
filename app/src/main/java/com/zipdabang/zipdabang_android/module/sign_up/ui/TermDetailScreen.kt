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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.sign_up.ui.component.CheckBoxWithText
import com.zipdabang.zipdabang_android.module.sign_up.ui.component.CheckBoxWithTextAndButton
import com.zipdabang.zipdabang_android.module.sign_up.ui.viewmodel.AuthSharedViewModel
import com.zipdabang.zipdabang_android.ui.component.AppBarSignUp
import com.zipdabang.zipdabang_android.ui.component.MainAndSubTitle
import com.zipdabang.zipdabang_android.ui.component.PrimaryButtonWithStatus
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun TermDetailScreen(
    navController: NavHostController,
    authSharedViewModel: AuthSharedViewModel = hiltViewModel(),
    onClickBack: ()->Unit,
    termIndex : Int,
) {
    val stateTermsForm = authSharedViewModel.stateTermsForm
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            AppBarSignUp(
                navigationIcon = R.drawable.ic_topbar_backbtn,
                onClickNavIcon = {
                   onClickBack()
                },
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
                    .fillMaxSize()
                    .verticalScroll(scrollState),
            ){
                Text(
                    text = stringResource(id = R.string.signup_terms_requiredone_title),
                    style = ZipdabangandroidTheme.Typography.twentytwo_700,
                    color = ZipdabangandroidTheme.Colors.Typo,
                    modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 10.dp)
                )

                for (i in 1..23) {
                    Box(
                        modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 10.dp)
                    ){
                        MainAndSubTitle(
                            mainValue = stringResource(id = getResourceId("signup_terms_requiredone_${i}_title")),
                            mainTextStyle = ZipdabangandroidTheme.Typography.twelve_700,
                            mainTextColor = ZipdabangandroidTheme.Colors.Typo,
                            subValue = stringResource(id = getResourceId("signup_terms_requiredone_${i}_sub")),
                            subTextStyle = ZipdabangandroidTheme.Typography.twelve_300,
                            subTextColor = ZipdabangandroidTheme.Colors.Typo
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun getResourceId(resourceName: String): Int {
    val context = LocalContext.current
    val packageName = context.packageName
    return context.resources.getIdentifier(resourceName, "string", packageName)
}
