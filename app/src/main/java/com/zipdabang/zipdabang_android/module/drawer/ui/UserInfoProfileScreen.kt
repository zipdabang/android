package com.zipdabang.zipdabang_android.module.drawer.ui

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.common.checkAndRequestPermissions
import com.zipdabang.zipdabang_android.module.drawer.ui.viewmodel.DrawerUserInfoViewModel
import com.zipdabang.zipdabang_android.module.my.ui.state.myrecipe.write.RecipeWriteFormEvent
import com.zipdabang.zipdabang_android.ui.component.AppBarSignUp
import com.zipdabang.zipdabang_android.ui.component.CustomDialogCameraFile
import com.zipdabang.zipdabang_android.ui.component.ModalDrawer
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream

@SuppressLint("UnrememberedMutableState")
@Composable
fun UserInfoProfileScreen(
    drawerUserInfoViewModel : DrawerUserInfoViewModel = hiltViewModel(),
    onClickBack : ()->Unit,
    onClickUserInfo : ()->Unit,
) {
    //drawer에 필요한 drawerState랑 scope
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var profilePhotoBitmap : Bitmap?
    var profile by mutableStateOf<MultipartBody.Part?>(null)
    var profileDialog = mutableStateOf(false)


    // 요청할 권한들에 대한 배열
    val permissions = arrayOf(
        Manifest.permission.CAMERA, // 카메라
        Manifest.permission.READ_MEDIA_IMAGES, // 갤러리
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

    // thumbnail
    // 갤러리 -> Uri 형태
    val takeThumbnailFromAlbumLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                val file: File? = uri.path?.let { File(it) }

                val inputStream: InputStream? = context.contentResolver?.openInputStream(uri) // 이미지에 대한 입력 스트림을 염
                val bitmap = BitmapFactory.decodeStream(inputStream) //uri -> bitmap 변환
                val byteOutputStream = ByteArrayOutputStream() // 이미지를 바이트 배열로 저장하기 위한 용도로 사용됨
                bitmap.compress(Bitmap.CompressFormat.JPEG, 10, byteOutputStream) //비트맵을 JPEG 형식으로 압축하고, 압축된 이미지를 byteOutPutStream에 저장함

                profilePhotoBitmap = bitmap

                // 압축된 이미지를 바이트 배열로 변환
                val profileRequestBody: RequestBody = byteOutputStream.toByteArray().toRequestBody("image/jpeg".toMediaTypeOrNull())
                // 이미지 데이터를 멀티파트로 변환
                val profilePart = MultipartBody.Part.createFormData("newProfile", "${profilePhotoBitmap}.jpeg", profileRequestBody)

                profile = profilePart
                Log.e("drawer-profile", "${profile} 들어갔담")

                CoroutineScope(Dispatchers.IO).launch {
                    drawerUserInfoViewModel.patchUserInfoProfile(profile!!)
                    withContext(Dispatchers.Main){
                        onClickUserInfo()
                    }
                }

            } else {
                Log.e("Error in camera", "No image selected")
            }
        }
    // 카메라->Bitmap 형태
    val takeThumbnailFromCameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { takenPhoto ->
            if (takenPhoto != null) {

                profilePhotoBitmap = takenPhoto

                val byteOutputStream = ByteArrayOutputStream() // 이미지를 바이트 배열로 저장하기 위한 용도로 사용됨
                takenPhoto.compress(Bitmap.CompressFormat.JPEG, 10, byteOutputStream) //비트맵을 JPEG 형식으로 압축하고, 압축된 이미지를 byteOutPutStream에 저장함

                // 압축된 이미지를 바이트 배열로 변환
                val profileRequestBody: RequestBody = byteOutputStream.toByteArray().toRequestBody("image/jpeg".toMediaTypeOrNull())
                // 이미지 데이터를 멀티파트로 변환
                val profilePart = MultipartBody.Part.createFormData("newProfile", "${profilePhotoBitmap}.jpeg", profileRequestBody)

                // 서버 업로드를 위한 thumbnailPart 변경
                profile = profilePart
                Log.e("drawer-profile", "${profile} 들어갔담")

                CoroutineScope(Dispatchers.IO).launch {
                    drawerUserInfoViewModel.patchUserInfoProfile(profile!!)
                    withContext(Dispatchers.Main){
                        onClickUserInfo()
                    }
                }
            }
            else {
                Log.e("Error in camera","error")
            }
        }




    ModalDrawer(
        scaffold = {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    AppBarSignUp(
                        navigationIcon = R.drawable.ic_topbar_backbtn,
                        onClickNavIcon = { onClickBack() },
                        centerText = stringResource(id = R.string.my_profileedit)
                    )
                },
                containerColor = Color.White,
                contentColor = Color.White,
                content = {
                    Column(
                        modifier = Modifier
                            .padding(it)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(48.dp)
                        ){
                            Column(
                                modifier = Modifier.clickable(onClick={
                                    profileDialog.value = true
                                }),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ){
                                AsyncImage(
                                    model = R.drawable.img_gallery,
                                    contentDescription = "직접 선택",
                                    modifier = Modifier.size(104.dp)
                                )
                                Text(
                                    text = stringResource(id = R.string.my_profileedit_select),
                                    style = ZipdabangandroidTheme.Typography.fourteen_500,
                                    color = ZipdabangandroidTheme.Colors.Typo
                                )
                            }
                            Column(
                                modifier = Modifier.clickable(onClick={
                                    CoroutineScope(Dispatchers.IO).launch {
                                        drawerUserInfoViewModel.patchUserInfoDefaultProfile()
                                        withContext(Dispatchers.Main){
                                            onClickUserInfo()
                                        }
                                    }
                                }),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ){
                                AsyncImage(
                                    model = R.drawable.img_profile,
                                    contentDescription = "기본 프로필",
                                    modifier = Modifier.size(104.dp)
                                )
                                Text(
                                    text = stringResource(id = R.string.my_profileedit_basicprofile),
                                    style = ZipdabangandroidTheme.Typography.fourteen_500,
                                    color = ZipdabangandroidTheme.Colors.Typo
                                )
                            }
                        }

                        // Profile 파일선택 알럿
                        if (profileDialog.value) {
                            CustomDialogCameraFile(
                                title = stringResource(id = R.string.my_dialog_fileselect),
                                setShowDialog = {
                                    profileDialog.value = it
                                },
                                onCameraClick = {
                                    checkAndRequestPermissions(
                                        context,
                                        permissions,
                                        launcherMultiplePermissions,
                                        isPermissionExist = {
                                            takeThumbnailFromCameraLauncher.launch()
                                            profileDialog.value = false
                                        }
                                    )
                                },
                                onFileClick = {
                                    checkAndRequestPermissions(
                                        context,
                                        permissions,
                                        launcherMultiplePermissions,
                                        isPermissionExist = {
                                            takeThumbnailFromAlbumLauncher.launch("image/*")
                                            profileDialog.value = false
                                        }
                                    )
                                }
                            )
                        }
                    }
                }
            )
        },
        drawerState = drawerState,
    )
}

@Preview
@Composable
fun PreviewUserInfoProfileScreen() {
    UserInfoProfileScreen(
        onClickBack={},
        onClickUserInfo = {}
    )
}