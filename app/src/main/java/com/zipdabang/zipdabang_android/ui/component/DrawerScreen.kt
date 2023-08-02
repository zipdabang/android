package com.zipdabang.zipdabang_android.ui.component

import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
//, scope: CoroutineScope
fun DrawerScreen(
    activityContentScope : @Composable (state: DrawerState, scope: CoroutineScope) -> Unit
){

    val state = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(0.8f),
        scrimColor = ZipdabangandroidTheme.Colors.MainBackground,
        gesturesEnabled = true,
        drawerState = state,
        drawerContent = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment =  Alignment.CenterHorizontally,
                ){
                Text(text="집다방 개발자 정보")
                Text(text="집다방 개발자 정보")
            }
        },
    ) {
        activityContentScope(state, scope)
    }


   /* val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            IconButton(
                onClick = { scope.launch{
                    drawerState.open()
                } },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "메뉴",
                )
            }
        },
        content = {paddingValues ->
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues) // Set the desired top padding here
            ) {
                activityContentScope(drawerState, scope)
            }
        }
    )

   ModalNavigationDrawer(
       drawerState = drawerState,
       scrimColor = ZipdabangandroidTheme.Colors.MainBackground,
       drawerContent = {
           Column {
               Text("집다방 개발자 정보")
               Text("집다방 개발자 정보")
           }
       }
   ){
       activityContentScope(drawerState, scope)
   }*/
}

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreviewDrawerScreen(){
    DrawerScreen{ state, scope ->
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ){
            Button(
                onClick = {
                    scope.launch{
                        state.open()
                    }
                }
            ){
                Text(text="open")
            }
        }

        BackHandler(
            enabled = state.currentValue == DrawerValue.Open,
            onBack = {
                scope.launch{
                    state.close()
                }
            }
        )
    }
   /* DrawerScreen { state, scope ->
        Column {
            Button(
                onClick = {
                    scope.launch{
                        state.open()
                    }
                }
            ) {
                Text(text = "open")
            }
        }

        BackHandler(
            enabled = state.currentValue == DrawerValue.Open,
            onBack = {
                scope.launch{
                    state.close()
                }
            }
        )
    }*/
}


