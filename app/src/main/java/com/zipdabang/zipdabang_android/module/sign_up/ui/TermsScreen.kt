package com.zipdabang.zipdabang_android.module.sign_up.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.sign_up.ui.viewmodel.AuthSharedViewModel
import com.zipdabang.zipdabang_android.ui.component.AppBarSignUp

@Composable
fun TermsScreen(
    navController: NavHostController,
    authSharedViewModel: AuthSharedViewModel
) {
    /*Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

    }*/

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppBarSignUp(
                navigationIcon = R.drawable.ic_topbar_backbtn,
                onClickNavIcon = {  },
                centerText = stringResource(id = R.string.signup)
            )
        }
    ) {
        Text(
            text = "dkkd",
            modifier = Modifier.padding(it))
    }
    
}

@Preview
@Composable
fun PreviewTermsScreen(){

    /*TermsScreen(
        navController = ,
        authSharedViewModel = AuthSharedViewModel()
    )*/
}