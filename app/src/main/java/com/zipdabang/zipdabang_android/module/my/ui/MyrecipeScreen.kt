package com.zipdabang.zipdabang_android.module.my.ui


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.ui.component.AppBarDefault
import com.zipdabang.zipdabang_android.ui.component.AppBarMy
import com.zipdabang.zipdabang_android.ui.component.AppBarSignUp
import com.zipdabang.zipdabang_android.ui.component.ModalDrawer
import kotlinx.coroutines.launch

@Composable
fun MyrecipeScreen(
    onClickBack : ()->Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalDrawer(
        scaffold = {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize(),
                topBar = {
                    AppBarDefault(
                        startIcon = R.drawable.ic_topbar_backbtn,
                        endIcon = R.drawable.ic_topbar_menu,
                        onClickStartIcon = { onClickBack() },
                        onClickEndIcon = { scope.launch { drawerState.open() } },
                        centerText = stringResource(id = R.string.my_myrecipe)
                    )
                },
                containerColor = Color.White,
                contentColor = Color.White,
            ){
                Surface(
                    modifier = Modifier.padding(it)
                ){

                }
            }
        },
        drawerState = drawerState
    )

}

@Preview
@Composable
fun PreviewMyrecipeScreen() {
    MyrecipeScreen(onClickBack = {})
}