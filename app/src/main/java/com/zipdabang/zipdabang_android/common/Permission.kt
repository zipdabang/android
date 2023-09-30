package com.zipdabang.zipdabang_android.common

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat

// https://hanyeop.tistory.com/452 참고 링크
fun checkAndRequestPermissions(
    context : Context,
    permissions : Array<String>,
    launcher : ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>
){
    // 권한이 이미 있는 경우
    if(permissions.all{
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }){
        Log.d("권한","권한이 이미 존재합니다.")
    }
    // 권한이 없는 경우
    else {
        launcher.launch(permissions)
    }
}

@Preview
@Composable
fun CheckAndRequestPermissions() {
    // context 정의
    val context = LocalContext.current
    // 요청할 권한들에 대한 배열
    val permissions = arrayOf(
        Manifest.permission.CAMERA, // 카메라
        Manifest.permission.READ_MEDIA_IMAGES, // 갤러리
        Manifest.permission.POST_NOTIFICATIONS, // 알람
        //Manifest.permission.ACCESS_FINE_LOCATION, // 위치
        //Manifest.permission.ACCESS_COARSE_LOCATION,
    )
    // 권한이 없을 경우 실행할 launcher 정의
    val launcherMultiplePermissions = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ){ permissionsMap ->
        val areGranted = permissionsMap.values.reduce { acc, next -> acc&&next }

        if(areGranted) {
            Log.d("권한","권한이 동의되었습니다.")
        } else{
            Log.d("권한","권한이 거부되었습니다.")
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Button(onClick = {
            checkAndRequestPermissions(
                context,
                permissions,
                launcherMultiplePermissions
            )
        }) {
            Text(text = "권한 요청하기")
        }
    }
}