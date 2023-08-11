package com.zipdabang.zipdabang_android.module.sign_up.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.zipdabang.zipdabang_android.core.navigation.AuthSharedViewModel

@Composable
fun TermsScreen(
    navController: NavHostController,
    authSharedViewModel: AuthSharedViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val email = authSharedViewModel.email.collectAsState()
        Text(text = "email: $email")
    }
}