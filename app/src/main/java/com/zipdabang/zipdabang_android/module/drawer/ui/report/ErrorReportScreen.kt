package com.zipdabang.zipdabang_android.module.drawer.ui.report

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.util.Patterns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.drawer.ui.viewmodel.ErrorReportViewModel
import com.zipdabang.zipdabang_android.ui.component.AppBarSignUp
import com.zipdabang.zipdabang_android.ui.component.CustomDialogCameraFile
import com.zipdabang.zipdabang_android.ui.component.PrimaryButtonWithStatus
import com.zipdabang.zipdabang_android.ui.component.TextFieldForDrawerMultiline
import com.zipdabang.zipdabang_android.ui.component.TextFieldForDrawerSingleline
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream

@SuppressLint("UnrememberedMutableState")
@Composable
fun ErrorReportScreen(
     isReportSuccess : () -> Unit,
     reportViewModel: ErrorReportViewModel = hiltViewModel()
){
    val reportState = reportViewModel.reportState

    val photoBitmap  = remember {
        mutableStateListOf<Bitmap?>()
    }

    val requestFileList  = remember {
        mutableStateListOf<MultipartBody.Part>()
    }

    val context : Context =  LocalContext.current

    var gallaryUri : Uri?  by remember{
        mutableStateOf(null)
    }

    var dialogShow by remember {
        mutableStateOf(false)
    }

    val takePhotoFromCameraLauncher =
        // 카메라로 사진 찍어서 가져오기
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { takenPhoto ->
            if (takenPhoto != null) {
                val byteOutputStream = ByteArrayOutputStream()
                takenPhoto.compress(
                    Bitmap.CompressFormat.JPEG,
                    10,
                    byteOutputStream
                )
                photoBitmap.add(takenPhoto)
                val requestBody : RequestBody = byteOutputStream.toByteArray()
                    .toRequestBody(
                        "image/jpeg".toMediaTypeOrNull()
                    )

                val uploadFile = MultipartBody.Part.createFormData("imageList","bg_${photoBitmap.size}.jpg",requestBody)

                requestFileList.add(uploadFile)
            } else {
                Log.e("Error in camera","error")
            }
        }

    val takePhotoFromAlbumIntent =
        Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
            putExtra(
                Intent.EXTRA_MIME_TYPES,
                arrayOf("image/jpeg", "image/png", "image/bmp", "image/webp")
            )
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
        }

    val takePhotoFromAlbumLauncher = // 갤러리에서 사진 가져오기
    rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                val file : File? = uri.path?.let { File(it) }

                gallaryUri= uri
                val inputSteam : InputStream? = gallaryUri?.let {
                    context.contentResolver?.openInputStream(
                        it
                    )
                }
                val  bitmap = BitmapFactory.decodeStream(inputSteam)
                val byteOutputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG,10,byteOutputStream)
                photoBitmap.add(bitmap)
                val requestBody : RequestBody = byteOutputStream.toByteArray()
                    .toRequestBody(
                        "image/jpeg".toMediaTypeOrNull()
                    )

                val uploadFile = MultipartBody.Part.createFormData("imageList","bg_${photoBitmap.size}.jpeg",requestBody)

                requestFileList.add(uploadFile)

            } ?: run {
                Log.e("Error in camera","error")

            }
        } else if (result.resultCode != Activity.RESULT_CANCELED) {
            Log.e("Error in camera","error")

        }
    }
    if(dialogShow){

        CustomDialogCameraFile(
            title ="파일 선택",
            setShowDialog = {
                            dialogShow = it
            } ,
            onCameraClick = { takePhotoFromCameraLauncher.launch()  },
            onFileClick = { takePhotoFromAlbumLauncher.launch(takePhotoFromAlbumIntent) }
            )

    }


    val scrollState = rememberScrollState()
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            AppBarSignUp(
                navigationIcon = R.drawable.ic_topbar_backbtn,
                onClickNavIcon = {  },
                centerText = "오류문의 및 신고"
            )
        }
    ) {
    Column(
        modifier = Modifier
            .padding(top = it.calculateTopPadding(), start = 16.dp, end = 16.dp, bottom = 40.dp)
            .verticalScroll(scrollState)
            .background(color = Color.White)
    ) {

        Text(
            text = "오류문의 및 신고",
            style = ZipdabangandroidTheme.Typography.twentytwo_700,
            color = ZipdabangandroidTheme.Colors.Typo
        )

        Text(
            text = "이름",
            style = ZipdabangandroidTheme.Typography.fourteen_500,
            color = ZipdabangandroidTheme.Colors.Typo,
            modifier = Modifier.padding(vertical = 10.dp)
        )
        var emailText by remember{
            mutableStateOf("")
        }
        var titleText by remember{
            mutableStateOf("")
        }
        var contentText by remember{
            mutableStateOf("")
        }
        var isFormFilled by remember {
            mutableStateOf(false)
        }
        var isError by remember {
            mutableStateOf(false)
        }



        Text(
            text = "답변 받을 이메일",
            style = ZipdabangandroidTheme.Typography.sixteen_700,
            color = ZipdabangandroidTheme.Colors.Typo
        )
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)) {


            TextFieldForDrawerSingleline(
                value = emailText,
                onValueChanged = { it, maxlength ->
                    emailText = it
                    isError = if(it=="") false
                    else !Patterns.EMAIL_ADDRESS.matcher(it).matches()
                },
                placeholderValue = "ex) zipdabang@gmail.com ",
                errorMessage = "이메일 형식이 맞지 않습니다.",
                maxLength = 30,
                isError = isError,
                imeAction = ImeAction.Default
            )
            if (emailText == "") {
                Text(
                    text = "ex) zipdabang@gmail.com",
                    style= ZipdabangandroidTheme.Typography.sixteen_300,
                    color = Color.Gray,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 18.dp, top = 5.dp)
                )
            }
        }
        Text(
            text = "문의 및 신고 내용",
            style = ZipdabangandroidTheme.Typography.sixteen_700,
            color = ZipdabangandroidTheme.Colors.Typo
        )
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
        ) {

            TextFieldForDrawerSingleline(
                value = titleText,
                onValueChanged = { it, maxlength ->
                    if (it.length <= maxlength) titleText = it
                },
                placeholderValue = "제목 (최대 20자) ",
                errorMessage = "",
                maxLength = 20,
                isError = false,
                imeAction = ImeAction.Default
            )
            if (titleText == "") {
            Text(
                text = "제목 (최대 20자)",
                style= ZipdabangandroidTheme.Typography.sixteen_300,
                color = Color.Gray,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 18.dp, top = 5.dp)
            )
        }
        }

        Spacer(modifier = Modifier.height(10.dp))

        TextFieldForDrawerMultiline(
            value = contentText,
            onValueChanged = {
                             it,maxlength ->
                if(it.length <= maxlength) contentText = it
            },
            placeholderValue = "내용 (최대 500자)" ,
            height = 500.dp ,
            maxLines = 40,
            maxLength = 500 ,
            isError = false,
            imeAction = ImeAction.Default
        )
        Spacer(modifier = Modifier.height(10.dp))

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(color = Color(0xFFF7F6F6), shape = ZipdabangandroidTheme.Shapes.small)
            .clickable {
                dialogShow = true
            }
        ){
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                .fillMaxSize()
                .padding(start = 12.dp)
            ){
                Icon(painter = painterResource(id = R.drawable.drawer_fileupload_small),contentDescription = null,tint = ZipdabangandroidTheme.Colors.Typo)
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = "파일 첨부하기", style = ZipdabangandroidTheme.Typography.sixteen_500,color = ZipdabangandroidTheme.Colors.Typo)


            }

        }

        Spacer(modifier = Modifier.height(10.dp))

        val rowScrollstate = rememberScrollState()

        if(photoBitmap.size != 0) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(104.dp)
                    .horizontalScroll(rowScrollstate),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                for (i in 0..4) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(104.dp)
                            .background(color = Color(0XFFF7F6F6), shape = RectangleShape)
                            .clickable {

                            }
                    ) {
                        if (photoBitmap.size > i) {
                            AsyncImage(
                                model = photoBitmap[i],
                                contentDescription = "bitmap",
                                modifier = Modifier.fillMaxSize()
                            )

                            Icon(
                                painter = painterResource(id = R.drawable.ic_all_delete_white),
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(5.dp)
                                    .clickable {
                                        photoBitmap.remove(photoBitmap[i])
                                        requestFileList.remove(requestFileList[i])
                                    }
                            )
                        }
                    }
                }

            }
        }
        Text(text= "첨부파일은 최대 5개, 각 3MB까지 등록 가능합니다.",
            style = ZipdabangandroidTheme.Typography.twelve_300,
            color= ZipdabangandroidTheme.Colors.Typo,modifier = Modifier.padding(vertical = 5.dp))

        Spacer(modifier = Modifier.height(40.dp))

        isFormFilled = emailText!="" && isError == false && titleText != "" && contentText != ""

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(36.dp)) {
            PrimaryButtonWithStatus(
                text = "제출하기",
                onClick = {reportViewModel.postReport(emailText,titleText,contentText,requestFileList.toList(),isReportSuccess)},
                isFormFilled = isFormFilled
            )
        }

        if(reportState.value.isLoading) {
            CircularProgressIndicator()
        }


    }




    }


}

@Composable
@Preview
fun ErrorReportPreviewScreen(){
   // ErrorReportScreen()
}