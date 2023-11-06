package com.zipdabang.zipdabang_android.module.my.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.common.checkAndRequestPermissions
import com.zipdabang.zipdabang_android.module.my.ui.component.recipewrite.ButtonAddForIngredient
import com.zipdabang.zipdabang_android.module.my.ui.component.recipewrite.IngredientAndUnit
import com.zipdabang.zipdabang_android.module.my.ui.component.recipewrite.Step
import com.zipdabang.zipdabang_android.module.my.ui.state.myrecipe.write.RecipeWriteBeveragesEvent
import com.zipdabang.zipdabang_android.module.my.ui.state.myrecipe.write.RecipeWriteDialogEvent
import com.zipdabang.zipdabang_android.module.my.ui.state.myrecipe.write.RecipeWriteFormEvent
import com.zipdabang.zipdabang_android.module.my.ui.viewmodel.RecipeWriteViewModel
import com.zipdabang.zipdabang_android.ui.component.AppBarSignUp
import com.zipdabang.zipdabang_android.ui.component.CustomDialogCameraFile
import com.zipdabang.zipdabang_android.ui.component.CustomDialogEditComplete
import com.zipdabang.zipdabang_android.ui.component.CustomDialogRecipeDelete
import com.zipdabang.zipdabang_android.ui.component.CustomDialogSelectCategory
import com.zipdabang.zipdabang_android.ui.component.CustomDialogType1
import com.zipdabang.zipdabang_android.ui.component.CustomDialogType2
import com.zipdabang.zipdabang_android.ui.component.CustomDialogUploadComplete
import com.zipdabang.zipdabang_android.ui.component.ImageWithIconAndText
import com.zipdabang.zipdabang_android.ui.component.PrimaryButtonOutLinedStatus
import com.zipdabang.zipdabang_android.ui.component.PrimaryButtonWithStatus
import com.zipdabang.zipdabang_android.ui.component.PrimaryButtonWithStatusForSignup
import com.zipdabang.zipdabang_android.ui.component.TextFieldForRecipeWriteMultiline
import com.zipdabang.zipdabang_android.ui.component.TextFieldForRecipeWriteSingleline
import com.zipdabang.zipdabang_android.ui.shimmeringEffect
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun RecipeWriteScreen(
    navController: NavHostController,
    tempId : Int?,
    recipeId : Int?,
    onClickBack: () -> Unit,
    onClickNextTimeInEdit : ()->Unit,
    recipeWriteViewModel: RecipeWriteViewModel = hiltViewModel(),
    onClickViewRecipe: (Int) -> Unit
) {
    // logic 관련
    val context = LocalContext.current
    val navController = rememberNavController()
    var tempRecipeApiCalled = recipeWriteViewModel.tempRecipeDetailApiCalled
    var completeRecipeApiCalled = recipeWriteViewModel.completeRecipeDetailApiCalled

    // state 정의
    val stateRecipeWriteForm = recipeWriteViewModel.stateRecipeWriteForm
    val stateRecipeWriteDialog = recipeWriteViewModel.stateRecipeWriteDialog
    val stateRecipeWriteBeverages = recipeWriteViewModel.stateRecipeWriteBeverages
    val stateThumbnail = recipeWriteViewModel.stateRecipeWriteForm.thumbnail
    val stateUploadRecipeId = recipeWriteViewModel.uploadRecipeId
    val stateEditRecipeId = recipeWriteViewModel.editRecipeId
    var shimmering: Boolean = true



    // 뒤로가기 제어
    BackHandler(
        enabled = true,
        onBack= {
            // 임시저장을 수정할 경우
            if(recipeWriteViewModel.tempRecipeDetailApiCalled > 0){
                if(recipeWriteViewModel.isTempRecipeEdited()){
                    recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.TempRecipeDeleteChanged(true))
                } else{
                    onClickBack()
                }
            }
            // 업로드를 수정할 경우
            else if(recipeWriteViewModel.completeRecipeDetailApiCalled >0){
                if(recipeWriteViewModel.isCompleteRecipeEdited()){
                    recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.CompleteRecipeDeleteChanged(true))
                } else{
                    onClickBack()
                }
            }
            // 처음 업로드 할 경우
            else {
                // 하나라도 차있는 경우
                if(!recipeWriteViewModel.isEmpty()){
                    recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.RecipeDeleteChanged(true))
                } else {
                    onClickBack()
                }
            }
        }
    )
    // shimmering을 위한 조건문
    if (stateRecipeWriteForm.isLoading || stateRecipeWriteForm.error.isNotBlank()) {
        shimmering = true
    } else {
        shimmering = false
    }
    // temp를 edit하는 상황인지 판단
    if(tempRecipeApiCalled == 0){
        if(tempId == 0 || tempId == null){}
        else {
            CoroutineScope(Dispatchers.Main).launch {
                recipeWriteViewModel.getTempRecipeDetail(tempId)
            }
        }
    }
    // complete를 edit하는 상황인지 판단
    if(completeRecipeApiCalled == 0){
        if(recipeId == 0 || recipeId == null){
            //Log.e("recipewrite-get-save", "첫번째 if문")
        }
        else{
            CoroutineScope(Dispatchers.Main).launch {
                recipeWriteViewModel.getCompleteRecipeDetail(recipeId)
               // Log.e("recipewrite-get-save", "두번째 if문")
            }
        }
    }

    // 임시저장과 업로드 버튼 활성화에 대한 launchedEffect
    LaunchedEffect(key1 = stateRecipeWriteForm){
        // 임시저장 일때
        if(tempRecipeApiCalled > 0){
            recipeWriteViewModel.onRecipeWriteFormEvent(RecipeWriteFormEvent.BtnEnabled(true))
            // 수정을 했다면
            if(recipeWriteViewModel.isTempRecipeEdited()) {
                recipeWriteViewModel.onRecipeWriteFormEvent(RecipeWriteFormEvent.BtnEnabledSave(true))
            }
        }
        // 업로드 된 레시피를 수정할때
        else if(completeRecipeApiCalled > 0){
            recipeWriteViewModel.onRecipeWriteFormEvent(RecipeWriteFormEvent.BtnEnabled(true))
        }
        // 처음 업로드할때
        else {
            recipeWriteViewModel.onRecipeWriteFormEvent(RecipeWriteFormEvent.BtnEnabled(true))
            recipeWriteViewModel.onRecipeWriteFormEvent(RecipeWriteFormEvent.BtnEnabledSave(true))
        }
    }
    // ingredient 추가 버튼 활성화에 대한 launchedEffect
    LaunchedEffect(key1 = stateRecipeWriteForm.ingredients) {
        recipeWriteViewModel.onRecipeWriteFormEvent(RecipeWriteFormEvent.BtnIngredientAddEnabled(stateRecipeWriteForm.ingredientsNum))
    }
    // step 추가 버튼 활성화에 대한 launchedEffect
    LaunchedEffect(key1= stateRecipeWriteForm.steps){
        recipeWriteViewModel.onRecipeWriteFormEvent(RecipeWriteFormEvent.StepIsValidate(stateRecipeWriteForm.stepsNum))
    }
    // 카테고리 알럿 버튼 활성화에 대한 launchedEffect
    LaunchedEffect(key1 = stateRecipeWriteBeverages){
        recipeWriteViewModel.onRecipeWriteBeveragesEvent(RecipeWriteBeveragesEvent.BtnEnabled(true))
    }

    // thumbnail, step에 대한 갤러리, 카메라 관련 변수
    var thumbnailPhotoBitmap : Bitmap?
    var stepPhotoBitmap : Bitmap?
    // 서버 업로드를 위한 List<MultiPartBody.Part>
    val stepImageParts  = remember {
        mutableStateListOf<MultipartBody.Part>()
    }
    // 몇번째 step의 이미지가 들어왔는지 확인하는 List<Int>
    val stepImageAddNum = remember {
        mutableStateListOf<Int>()
    }




    /* fun bitmapToUri(context: Context, bitmap: Bitmap): Uri? {
        val imagesDir = File(context.cacheDir, "images")
        if (!imagesDir.exists()) {
            imagesDir.mkdirs()
        }

        val imageFile = File(imagesDir, "image_${System.currentTimeMillis()}.jpg")

        try {
            val fileOutputStream = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
            fileOutputStream.flush()
            fileOutputStream.close()
            return FileProvider.getUriForFile(context, "zipdabang.fileprovider", imageFile)
        } catch (e: IOException) {
            throw e
        }

    }*/

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
    // 이미지의 Exif 정보를 확인하고 회전 각도를 반환하는 함수
    fun getExifOrientation(filePath : String) : Int{
        try{
            val exif = ExifInterface(filePath)
            val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)

            return when(orientation){
                ExifInterface.ORIENTATION_ROTATE_90 -> 90
                ExifInterface.ORIENTATION_ROTATE_180 -> 180
                ExifInterface.ORIENTATION_ROTATE_270 -> 270
                else -> 0
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 0
    }
    // 이미지를 회전시키는 함수
    fun rotateBitmap(bitmap: Bitmap, degrees : Int) : Bitmap{
//        val matrix = Matrix()
//        matrix.postRotate(degrees.toFloat())
//        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    // thumbnail
    // 갤러리 -> Uri 형태
    val takeThumbnailFromAlbumLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                val file: File? = uri.path?.let { File(it) }

                // 프론트 측에서 thumbnail 변경
                recipeWriteViewModel.onRecipeWriteFormEvent(RecipeWriteFormEvent.ThumbnailChanged(uri))

                val inputStream: InputStream? = context.contentResolver?.openInputStream(uri) // 이미지에 대한 입력 스트림을 염
                val bitmap = BitmapFactory.decodeStream(inputStream) //uri -> bitmap 변환

                val byteOutputStream = ByteArrayOutputStream() // 이미지를 바이트 배열로 저장하기 위한 용도로 사용됨
                bitmap.compress(Bitmap.CompressFormat.JPEG, 10, byteOutputStream) //비트맵을 JPEG 형식으로 압축하고, 압축된 이미지를 byteOutPutStream에 저장함

                thumbnailPhotoBitmap = bitmap

                // 압축된 이미지를 바이트 배열로 변환
                val thumbnailRequestBody: RequestBody = byteOutputStream.toByteArray().toRequestBody("image/jpeg".toMediaTypeOrNull())
                // 이미지 데이터를 멀티파트로 변환
                val thumbnailPart = MultipartBody.Part.createFormData("thumbnail", "thumbnail_${System.currentTimeMillis()}.jpeg", thumbnailRequestBody)
                Log.e("recipewriteform-thumbnail","${thumbnailRequestBody}.jpeg")

                // 서버 업로드를 위한 thumbnailPart 변경
                recipeWriteViewModel.thumbnailPart = thumbnailPart
            } else {
                Log.e("Error in camera", "No image selected")
            }
        }
    // 카메라->Bitmap 형태
    val takeThumbnailFromCameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { takenPhoto ->
            if (takenPhoto != null) {

                // 프론트 측에서 thumbnail 변경
                // val photoUri = bitmapToUri(context, takenPhoto)
                recipeWriteViewModel.onRecipeWriteFormEvent(RecipeWriteFormEvent.ThumbnailChanged(takenPhoto))

                val byteOutputStream = ByteArrayOutputStream() // 이미지를 바이트 배열로 저장하기 위한 용도로 사용됨
                takenPhoto.compress(Bitmap.CompressFormat.JPEG, 10, byteOutputStream) //비트맵을 JPEG 형식으로 압축하고, 압축된 이미지를 byteOutPutStream에 저장함

                thumbnailPhotoBitmap = takenPhoto

                // 압축된 이미지를 바이트 배열로 변환
                val thumbnailRequestBody: RequestBody = byteOutputStream.toByteArray().toRequestBody("image/jpeg".toMediaTypeOrNull())
                // 이미지 데이터를 멀티파트로 변환
                val thumbnailPart = MultipartBody.Part.createFormData("thumbnail", "thumbnail_${System.currentTimeMillis()}.jpeg", thumbnailRequestBody)
                Log.e("recipewriteform-thumbnail","${thumbnailRequestBody}.jpeg")

                // 서버 업로드를 위한 thumbnailPart 변경
                recipeWriteViewModel.thumbnailPart = thumbnailPart
            }
            else {
                Log.e("Error in camera","error")
            }
        }

    // step
    // 갤러리 -> Uri 형태
    val takeStepPhotoFromAlbumLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                val file: File? = uri.path?.let { File(it) }

                // 프론트 측에서 step 변경
                recipeWriteViewModel.onRecipeWriteFormEvent(RecipeWriteFormEvent.StepImageChanged(uri, stateRecipeWriteDialog.stepNum))

                val inputStream: InputStream? = context.contentResolver?.openInputStream(uri) // 이미지에 대한 입력 스트림을 염
                val bitmap = BitmapFactory.decodeStream(inputStream) //uri -> bitmap 변환
                val byteOutputStream = ByteArrayOutputStream() // 이미지를 바이트 배열로 저장하기 위한 용도로 사용됨
                bitmap.compress(Bitmap.CompressFormat.JPEG, 10, byteOutputStream) //비트맵을 JPEG 형식으로 압축하고, 압축된 이미지를 byteOutPutStream에 저장함

                stepPhotoBitmap = bitmap

                // 압축된 이미지를 바이트 배열로 변환
                val stepRequestBody: RequestBody = byteOutputStream.toByteArray().toRequestBody("image/jpeg".toMediaTypeOrNull())
                // 이미지 데이터를 멀티파트로 변환
                val stepPart = MultipartBody.Part.createFormData("stepImages", "${stateRecipeWriteDialog.stepNum - 1}_${System.currentTimeMillis()}.jpeg", stepRequestBody)
                Log.e("recipewriteform-stepImages","${stateRecipeWriteDialog.stepNum - 1}_${System.currentTimeMillis()}.jpeg")

                // 이미 존재하는 경우, 기존 데이터를 지우고 새로운 데이터 추가
                if (stepImageAddNum.contains(stateRecipeWriteDialog.stepNum)) {
                    val existingIndex = stepImageAddNum.indexOf(stateRecipeWriteDialog.stepNum)
                    stepImageParts[existingIndex] = stepPart
                }
                else {
                    stepImageAddNum.add(stateRecipeWriteDialog.stepNum)
                    stepImageParts.add(stepPart)
                }
            } else {
                Log.e("Error in camera", "No image selected")
            }
        }
    // 카메라->Bitmap 형태
    val takeStepPhotoFromCameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { takenPhoto ->
            if (takenPhoto != null) {

                // 프론트 측에서 step 변경
                recipeWriteViewModel.onRecipeWriteFormEvent(RecipeWriteFormEvent.StepImageChanged(takenPhoto, stateRecipeWriteDialog.stepNum))

                val byteOutputStream = ByteArrayOutputStream() // 이미지를 바이트 배열로 저장하기 위한 용도로 사용됨
                takenPhoto.compress(Bitmap.CompressFormat.JPEG, 10, byteOutputStream) //비트맵을 JPEG 형식으로 압축하고, 압축된 이미지를 byteOutPutStream에 저장함

                stepPhotoBitmap = takenPhoto

                // 압축된 이미지를 바이트 배열로 변환
                val stepRequestBody: RequestBody = byteOutputStream.toByteArray().toRequestBody("image/jpeg".toMediaTypeOrNull())
                // 이미지 데이터를 멀티파트로 변환
                val stepPart = MultipartBody.Part.createFormData("stepImages", "${stateRecipeWriteDialog.stepNum - 1}_${System.currentTimeMillis()}.jpeg", stepRequestBody)
                Log.e("recipewriteform-stepImages","${stateRecipeWriteDialog.stepNum - 1}_${System.currentTimeMillis()}.jpeg")


                // 이미 존재하는 경우, 기존 데이터를 지우고 새로운 데이터 추가
                if (stepImageAddNum.contains(stateRecipeWriteDialog.stepNum)) {
                    val existingIndex = stepImageAddNum.indexOf(stateRecipeWriteDialog.stepNum)
                    stepImageParts[existingIndex] = stepPart
                }
                else {
                    stepImageAddNum.add(stateRecipeWriteDialog.stepNum)
                    stepImageParts.add(stepPart)
                }
            }
            else {
                Log.e("Error in camera","error")
            }
        }



    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            // 하단 버튼
            if(shimmering){
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(72.dp)
                        .padding(16.dp, 12.dp, 16.dp, 12.dp)
                        .shimmeringEffect(),
                )
            }
            // 업로드 된 레시피 수정할 때
            else if(completeRecipeApiCalled > 0){
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 12.dp, 16.dp, 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    PrimaryButtonWithStatusForSignup(
                        isFormFilled = stateRecipeWriteForm.btnEnabled,
                        text = "수정 완료",
                        onClick = {
                            recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.UploadCategoryChanged(true))
                         },
                    )
                }
            }
            // 임시저장 수정할때, 처음 업로드할때
            else{
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 12.dp, 16.dp, 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        PrimaryButtonOutLinedStatus(
                            enabled = stateRecipeWriteForm.btnEnabledSave,
                            borderColor = ZipdabangandroidTheme.Colors.MainBackground,
                            text = stringResource(id = R.string.my_recipewrite_save),
                            onClick = {
                                recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.SaveChanged(true))
                            },
                        )
                    }
                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        PrimaryButtonWithStatusForSignup(
                            isFormFilled = stateRecipeWriteForm.btnEnabled,
                            text = stringResource(id = R.string.my_recipewrite_writedone),
                            onClick = {
                                recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.UploadCategoryChanged(true))
                            },
                        )
                    }

                }
            }
        },
        topBar = {
            AppBarSignUp(
                navigationIcon = R.drawable.ic_topbar_backbtn,
                onClickNavIcon = {
                    // 임시저장을 수정할 경우
                    if(recipeWriteViewModel.tempRecipeDetailApiCalled > 0){
                        if(recipeWriteViewModel.isTempRecipeEdited()){
                            recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.TempRecipeDeleteChanged(true))
                        } else{
                            onClickBack()
                        }
                    }
                    // 업로드를 수정할 경우
                    else if(recipeWriteViewModel.completeRecipeDetailApiCalled >0){
                        if(recipeWriteViewModel.isCompleteRecipeEdited()){
                            recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.CompleteRecipeDeleteChanged(true))
                        } else{
                            onClickBack()
                        }
                    }
                    // 처음 업로드 할 경우
                    else {
                        // 하나라도 차있는 경우
                        if(!recipeWriteViewModel.isEmpty()){
                            recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.RecipeDeleteChanged(true))
                        } else {
                            onClickBack()
                        }
                    }
                },
                centerText = stringResource(id = R.string.my_recipewrite)
            )
        },
        containerColor = Color.White,
        contentColor = Color.White,
    ) {
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(Color.White)
        ) {
            // 썸네일
            if(shimmering){
                Box(
                    modifier = Modifier
                        .height(360.dp)
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .shimmeringEffect()
                )
            }
            else{
                Box(
                    modifier = Modifier
                        .height(360.dp)
                        .fillMaxWidth()
                        .aspectRatio(1f)
                ) {
                    ImageWithIconAndText(
                        addImageClick = {
                            recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.FileSelectChanged(true))
                        },
                        deleteImageClick = {
                            recipeWriteViewModel.onRecipeWriteFormEvent(RecipeWriteFormEvent.ThumbnailChangedToNull(null))
                        },
                        imageUrl = stateThumbnail,
                        iconImageVector = R.drawable.ic_recipewrite_camera,
                        iconTint = ZipdabangandroidTheme.Colors.Typo.copy(0.5f),
                        iconModifier = Modifier.size(27.dp, 24.dp),
                        text = stringResource(id = R.string.my_recipewrite_thumbnail_upload),
                        textStyle = ZipdabangandroidTheme.Typography.sixteen_700,
                        textColor = ZipdabangandroidTheme.Colors.Typo.copy(0.5f)
                    )
                }
            }

            // 썸네일 외
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 20.dp, 16.dp, 10.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // 레시피 제목
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.my_recipewrite_title),
                        style = ZipdabangandroidTheme.Typography.sixteen_700,
                        color = ZipdabangandroidTheme.Colors.Choco,
                    )
                    if(shimmering){
                        Box(
                            modifier = Modifier
                                .shimmeringEffect()
                                .height(56.dp)
                                .fillMaxWidth()
                        )
                    }
                    else{
                        TextFieldForRecipeWriteSingleline(
                            value = stateRecipeWriteForm.title,
                            onValueChanged = { newText, maxLength ->
                                if (newText.length <= maxLength) {
                                    recipeWriteViewModel.onRecipeWriteFormEvent(
                                        RecipeWriteFormEvent.TitleChanged(
                                            newText
                                        )
                                    )
                                }
                            },
                            maxLength = 20,
                            placeholderValue = stringResource(id = R.string.my_recipewrite_title_hint),
                            imeAction = ImeAction.Next,
                            onClickTrailingicon = {
                                recipeWriteViewModel.onRecipeWriteFormEvent(
                                    RecipeWriteFormEvent.TitleChanged(
                                        ""
                                    )
                                )
                            }
                        )
                    }
                    if(shimmering){
                        Box(modifier = Modifier
                            .align(Alignment.End)
                            .width(32.dp)
                            .height(20.dp)
                            .shimmeringEffect())
                    }
                    else{
                        Text(
                            modifier = Modifier
                                .align(Alignment.End)
                                .padding(0.dp, 0.dp, 4.dp, 0.dp),
                            text = stateRecipeWriteForm.titleWordCount.toString() + "/20",
                            style = ZipdabangandroidTheme.Typography.fourteen_300,
                            color = ZipdabangandroidTheme.Colors.Typo
                        )
                    }
                }

                // 소요 시간
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.my_recipewrite_time),
                        style = ZipdabangandroidTheme.Typography.sixteen_700,
                        color = ZipdabangandroidTheme.Colors.Choco,
                    )
                    if(shimmering){
                        Box(
                            modifier = Modifier
                                .shimmeringEffect()
                                .height(56.dp)
                                .fillMaxWidth()
                        )
                    }
                    else{
                        TextFieldForRecipeWriteSingleline(
                            value = stateRecipeWriteForm.time,
                            onValueChanged = { newText, maxLength ->
                                if (newText.length <= maxLength) {
                                    recipeWriteViewModel.onRecipeWriteFormEvent(
                                        RecipeWriteFormEvent.TimeChanged(
                                            newText
                                        )
                                    )
                                }
                            },
                            maxLength = 20,
                            placeholderValue = stringResource(id = R.string.my_recipewrite_time),
                            imeAction = ImeAction.Next,
                            onClickTrailingicon = {
                                recipeWriteViewModel.onRecipeWriteFormEvent(
                                    RecipeWriteFormEvent.TimeChanged(
                                        ""
                                    )
                                )
                            }
                        )
                    }
                    if(shimmering){
                        Box(modifier = Modifier
                            .align(Alignment.End)
                            .width(32.dp)
                            .height(20.dp)
                            .shimmeringEffect())
                    }
                    else {
                        Text(
                            modifier = Modifier
                                .align(Alignment.End)
                                .padding(0.dp, 0.dp, 4.dp, 0.dp),
                            text = stateRecipeWriteForm.timeWordCount.toString() + "/20",
                            style = ZipdabangandroidTheme.Typography.fourteen_300,
                            color = ZipdabangandroidTheme.Colors.Typo
                        )
                    }
                }

                // 레시피 한 줄 소개
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.my_recipewrite_intro),
                        style = ZipdabangandroidTheme.Typography.sixteen_700,
                        color = ZipdabangandroidTheme.Colors.Choco
                    )
                    if(shimmering){
                        Box(
                            modifier = Modifier
                                .shimmeringEffect()
                                .height(168.dp)
                                .fillMaxWidth()
                        )
                    }
                    else{
                        TextFieldForRecipeWriteMultiline(
                            value = stateRecipeWriteForm.intro,
                            onValueChanged = { newText, maxLength ->
                                if (newText.length <= maxLength) {
                                    recipeWriteViewModel.onRecipeWriteFormEvent(
                                        RecipeWriteFormEvent.IntroChanged(
                                            newText
                                        )
                                    )
                                }
                            },
                            height = 128.dp,
                            maxLines = 4,
                            maxLength = 100,
                            placeholderValue = stringResource(id = R.string.my_recipewrite_intro_hint),
                            imeAction = ImeAction.None,
                        )
                    }
                    if(shimmering){
                        Box(modifier = Modifier
                            .align(Alignment.End)
                            .width(32.dp)
                            .height(20.dp)
                            .shimmeringEffect())
                    }
                    else{
                        Text(
                            modifier = Modifier
                                .align(Alignment.End)
                                .padding(0.dp, 0.dp, 4.dp, 0.dp),
                            text = stateRecipeWriteForm.introWordCount.toString() + "/100",
                            style = ZipdabangandroidTheme.Typography.fourteen_300,
                            color = ZipdabangandroidTheme.Colors.Typo
                        )
                    }
                }

                // 재료
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.my_recipewrite_ingredient),
                        style = ZipdabangandroidTheme.Typography.sixteen_700,
                        color = ZipdabangandroidTheme.Colors.Choco
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            modifier = Modifier
                                .weight(3f)
                                .padding(4.dp, 0.dp, 0.dp, 0.dp),
                            text = stringResource(id = R.string.my_recipewrite_ingredient),
                            style = ZipdabangandroidTheme.Typography.fourteen_500,
                            color = ZipdabangandroidTheme.Colors.Typo
                        )
                        Text(
                            modifier = Modifier
                                .weight(4f)
                                .padding(4.dp, 0.dp, 0.dp, 0.dp),
                            text = stringResource(id = R.string.my_recipewrite_unit),
                            style = ZipdabangandroidTheme.Typography.fourteen_500,
                            color = ZipdabangandroidTheme.Colors.Typo
                        )
                    }
                    if(shimmering){
                        Row(){
                            Box(
                                modifier = Modifier
                                    .shimmeringEffect()
                                    .height(56.dp)
                                    .weight(3f)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Box(
                                modifier = Modifier
                                    .shimmeringEffect()
                                    .height(56.dp)
                                    .weight(3f)
                            )
                            Box(
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                    else {
                        for (i in 0 until stateRecipeWriteForm.ingredientsNum) {
                            IngredientAndUnit(
                                ingredientNum = i+1,
                                valueIngredient = stateRecipeWriteForm.ingredients[i].ingredientName,
                                onValueChangedIngredient = { newText, maxLength ->
                                    if (newText.length <= maxLength) {
                                        recipeWriteViewModel.onRecipeWriteFormEvent(
                                            RecipeWriteFormEvent.IngredientChanged(newText,i+1))
                                    }
                                },
                                placeholderValueIngredient = stringResource(id = R.string.my_recipewrite_milk),
                                maxLengthIngredient = 16,
                                imeActionIngredient = ImeAction.Next,
                                onClickTrailingiconIngredient = {
                                    recipeWriteViewModel.onRecipeWriteFormEvent(RecipeWriteFormEvent.IngredientChanged("",i+1))
                                },

                                onClickCancelIngredient = {
                                    recipeWriteViewModel.onRecipeWriteFormEvent(RecipeWriteFormEvent.BtnIngredientDelete(i+1))
                                },

                                valueUnit = stateRecipeWriteForm.ingredients[i].quantity,
                                onValueChangedUnit = { newText, maxLength ->
                                    if (newText.length <= maxLength) {
                                        recipeWriteViewModel.onRecipeWriteFormEvent(
                                            RecipeWriteFormEvent.QuantityChanged(newText,i+1))
                                    }
                                },
                                placeholderValueUnit = stringResource(id = R.string.my_recipewrite_hundredmilli),
                                maxLengthUnit = 16,
                                imeActionUnit = ImeAction.Default,
                                onClickTrailingiconUnit = {
                                    recipeWriteViewModel.onRecipeWriteFormEvent(RecipeWriteFormEvent.QuantityChanged("",i+1))
                                }
                            )
                        }
                    }
                    if(stateRecipeWriteForm.ingredientsNum == 10){ }
                    else {
                        ButtonAddForIngredient(
                            enabled = stateRecipeWriteForm.ingredientBtnEnabled,
                            onClickBtn = {
                                recipeWriteViewModel.onRecipeWriteFormEvent(
                                    RecipeWriteFormEvent.BtnIngredientAdd(
                                        ingredientNum = stateRecipeWriteForm.ingredientsNum + 1
                                    )
                                )
                            }
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 0.dp, 0.dp, 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if(stateRecipeWriteForm.ingredientsNum == 10){
                            Row(
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_recipewrite_warning),
                                    contentDescription = "Icon",
                                    tint = Color(0xFFB00020),
                                    modifier = Modifier
                                        .size(16.dp)
                                        .padding(4.dp, 0.dp, 0.dp, 0.dp),
                                )
                                Text(
                                    modifier = Modifier.padding(2.dp, 0.dp, 0.dp, 0.dp),
                                    text = stringResource(id = R.string.my_recipewrite_warning_ingredient),
                                    style = ZipdabangandroidTheme.Typography.twelve_500,
                                    color = Color(0xFFB00020)
                                )
                            }
                        } else{
                            Box(
                                modifier = Modifier.size(22.dp, 16.dp)
                            )
                        }
                        if(shimmering){
                            Box(modifier = Modifier
                                .width(52.dp)
                                .height(20.dp)
                                .shimmeringEffect())
                        }
                        else{
                            Text(
                                modifier = Modifier.padding(0.dp, 0.dp, 4.dp, 0.dp),
                                text = stateRecipeWriteForm.ingredientsNum.toString() + "/10",
                                style = ZipdabangandroidTheme.Typography.fourteen_300,
                                color = ZipdabangandroidTheme.Colors.Typo
                            )
                        }
                    }
                }

                // 레시피 순서
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.my_recipewrite_step),
                        style = ZipdabangandroidTheme.Typography.sixteen_700,
                        color = ZipdabangandroidTheme.Colors.Choco
                    )
                    if(shimmering){
                        Column{
                            Box(
                                modifier = Modifier
                                    .shimmeringEffect()
                                    .height(56.dp)
                                    .fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Box(
                                modifier = Modifier
                                    .shimmeringEffect()
                                    .height(260.dp)
                                    .fillMaxWidth()
                            )
                        }
                    }
                    else{
                        for (i in 0 until stateRecipeWriteForm.stepsNum) {
                            Step(
                                stepNum = i+1,
                                stepImage = stateRecipeWriteForm.steps[i].stepImage,
                                value = stateRecipeWriteForm.steps[i].description,
                                valueLength = stateRecipeWriteForm.steps[i].stepWordCount.toString(),
                                onValueChanged = { newText, maxLength ->
                                    if (newText.length <= maxLength) {
                                        recipeWriteViewModel.onRecipeWriteFormEvent(
                                            RecipeWriteFormEvent.StepChanged(newText, i+1))
                                    }
                                },
                                placeholderValue = "레시피를 만드는 Step "+(i+1)+"을 설명해 주세요. \n(최대 200자)",
                                completeBtnVisible = stateRecipeWriteForm.steps[i].completeBtnVisible,
                                completeBtnEnabled = stateRecipeWriteForm.steps[i].completeBtnEnabled,
                                addBtnVisible = stateRecipeWriteForm.steps[i].addBtnVisible,
                                height = 232.dp,
                                maxLines = 7,
                                maxLength = 200,
                                imeAction = ImeAction.None,
                                onClickImageAddBtn = {
                                    recipeWriteViewModel.onRecipeWriteDialogEvent(
                                        RecipeWriteDialogEvent.StepFileSelectChanged(true, i+1))
                                },
                                onClickDeleteStep = {
                                    recipeWriteViewModel.onRecipeWriteFormEvent(RecipeWriteFormEvent.BtnStepDelete(stepNum = i+1 ))
                                },
                                onClickEditStep = {
                                    recipeWriteViewModel.onRecipeWriteFormEvent(RecipeWriteFormEvent.BtnStepEdit(stepNum = i+1 ))
                                },
                                onClickComplete = {
                                    recipeWriteViewModel.onRecipeWriteFormEvent(RecipeWriteFormEvent.BtnStepComplete(stepNum = i+1 ))
                                },
                                onClickAdd = {
                                    recipeWriteViewModel.onRecipeWriteFormEvent(RecipeWriteFormEvent.BtnStepAdd(stepNum = i+1 ))
                                }
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 0.dp, 0.dp, 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if(stateRecipeWriteForm.stepsNum == 10){
                            Row(
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_recipewrite_warning),
                                    contentDescription = "Icon",
                                    tint = Color(0xFFB00020),
                                    modifier = Modifier
                                        .size(16.dp)
                                        .padding(4.dp, 0.dp, 0.dp, 0.dp),
                                )
                                Text(
                                    modifier = Modifier.padding(2.dp, 0.dp, 0.dp, 0.dp),
                                    text = stringResource(id = R.string.my_recipewrite_warning_stepNum),
                                    style = ZipdabangandroidTheme.Typography.twelve_500,
                                    color = Color(0xFFB00020)
                                )
                            }
                        } else{
                            Box(
                                modifier = Modifier.size(22.dp, 16.dp)
                            )
                        }
                        if(shimmering){
                            Box(modifier = Modifier
                                .width(32.dp)
                                .height(20.dp)
                                .shimmeringEffect())
                        }
                        else{
                            Text(
                                modifier = Modifier.padding(0.dp, 0.dp, 4.dp, 0.dp),
                                text = "Step" + stateRecipeWriteForm.stepsNum + "/Step10",
                                style = ZipdabangandroidTheme.Typography.fourteen_300,
                                color = ZipdabangandroidTheme.Colors.Typo
                            )
                        }
                    }

                }

                // 레시피 Tip
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.my_recipewrite_recipetip),
                        style = ZipdabangandroidTheme.Typography.sixteen_700,
                        color = ZipdabangandroidTheme.Colors.Choco
                    )
                    if(shimmering){
                        Box(
                            modifier = Modifier
                                .shimmeringEffect()
                                .height(260.dp)
                                .fillMaxWidth()
                        )
                    }
                    else{
                        TextFieldForRecipeWriteMultiline(
                            value = stateRecipeWriteForm.recipeTip,
                            onValueChanged = { newText, maxLength ->
                                if (newText.length <= maxLength) {
                                    recipeWriteViewModel.onRecipeWriteFormEvent(
                                        RecipeWriteFormEvent.RecipeTipChanged(
                                            newText
                                        )
                                    )
                                }
                            },
                            height = 224.dp,
                            maxLines = 8,
                            maxLength = 200,
                            placeholderValue = stringResource(id = R.string.my_recipewrite_recipetip_hint),
                            imeAction = ImeAction.None,
                        )
                    }
                    if(shimmering){
                        Box(modifier = Modifier
                            .align(Alignment.End)
                            .width(32.dp)
                            .height(20.dp)
                            .shimmeringEffect())
                    }
                    else{
                        Text(
                            modifier = Modifier
                                .align(Alignment.End)
                                .padding(0.dp, 0.dp, 4.dp, 0.dp),
                            text = stateRecipeWriteForm.recipeTipWordCount.toString() + "/200",
                            style = ZipdabangandroidTheme.Typography.fourteen_300,
                            color = ZipdabangandroidTheme.Colors.Typo
                        )
                    }
                }
            }
        }


        // 알럿
        // 권한허용 알럿
        if (stateRecipeWriteDialog.isOpenPermission) {
            CustomDialogType1(
                title = stringResource(id = R.string.my_dialog_permission),
                text = stringResource(id = R.string.my_dialog_permission_detail),
                declineText = stringResource(id = R.string.my_dialog_cancel),
                acceptText = stringResource(id = R.string.my_dialog_permit),
                setShowDialog = {
                    recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.PermissionChanged(it))
                },
                onAcceptClick = {
                    // 카메라 허용받기
                    recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.PermissionChanged(false))
                }
            )
        }
        // Thumbnail 파일선택 알럿
        if (stateRecipeWriteDialog.isOpenFileSelect) {
            CustomDialogCameraFile(
                title = stringResource(id = R.string.my_dialog_fileselect),
                setShowDialog = {
                    recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.FileSelectChanged(it))
                },
                onCameraClick = {
                    checkAndRequestPermissions(
                        context,
                        permissions,
                        launcherMultiplePermissions,
                        isPermissionExist = {
                            takeThumbnailFromCameraLauncher.launch()
                            recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.FileSelectChanged(false))
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
                            recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.FileSelectChanged(false))
                        }
                    )
                }
            )
        }
        // Step 파일선택 알럿
        if (stateRecipeWriteDialog.isOpenStepFileSelect) {
            CustomDialogCameraFile(
                title = stringResource(id = R.string.my_dialog_fileselect),
                setShowDialog = {
                    recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.StepFileSelectChanged(it, stateRecipeWriteDialog.stepNum))
                },
                onCameraClick = {
                    checkAndRequestPermissions(
                        context,
                        permissions,
                        launcherMultiplePermissions,
                        isPermissionExist = {
                            takeStepPhotoFromCameraLauncher.launch()
                            recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.StepFileSelectChanged(false, stateRecipeWriteDialog.stepNum))
                        }
                    )
                },
                onFileClick = {
                    checkAndRequestPermissions(
                        context,
                        permissions,
                        launcherMultiplePermissions,
                        isPermissionExist = {
                            takeStepPhotoFromAlbumLauncher.launch("image/*")
                            recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.StepFileSelectChanged(false, stateRecipeWriteDialog.stepNum))
                        }
                    )
                }
            )
        }
        // 임시저장 알럿
        if (stateRecipeWriteDialog.isOpenSave) {
            CustomDialogType2(
                title = stringResource(id = R.string.my_save),
                text = "작성 중인 레시피를 임시저장 하시겠습니까?\n" +
                        "저장된 레시피는 ‘내집다방 > 나의 레시피 > 임시저장’에서 확인 하실 수 있습니다.",
                declineText = stringResource(id = R.string.my_dialog_cancel),
                acceptText = stringResource(id = R.string.my_save),
                setShowDialog = {
                    recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.SaveChanged(it))
                },
                onAcceptClick = {
                    // 임시저장한 레시피일 경우
                    if(tempRecipeApiCalled > 0){
                        CoroutineScope(Dispatchers.Main).launch {
                            recipeWriteViewModel.postTempRecipeToTemp(tempId!!, stepImageParts.toList(), stepImageAddNum)
                        }
                    }
                    else{
                        CoroutineScope(Dispatchers.Main).launch {
                            recipeWriteViewModel.postRecipeWriteTemp(stepImageParts = stepImageParts.toList())
                        }
                    }
                    onClickBack()
                    recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.SaveChanged(false))
                }
            )
        }
        // 카테고리 선택 알럿
        if (stateRecipeWriteDialog.isOpenUploadCategory) {
            CustomDialogSelectCategory(
                categoryList = stateRecipeWriteBeverages.beverageList,
                categoryParagraphList = listOf(3, 2, 2, 1),
                categorySelectedList = stateRecipeWriteBeverages.beverageCheckList,
                onSelectClick = { index, clicked ->
                    recipeWriteViewModel.onRecipeWriteBeveragesEvent(RecipeWriteBeveragesEvent.StepFileSelectChanged(index, clicked))
                },
                isComplete = stateRecipeWriteBeverages.btnEnabled,
                onCompleteClick = {
                    // 임시저장을 업로드할 경우
                    if(tempRecipeApiCalled > 0) {
                        CoroutineScope(Dispatchers.Main).launch {
                            // temppost api success됐는지 확인하는 변수
                            val isTempSuccess = recipeWriteViewModel.postTempRecipeToTemp(tempId!!, stepImageParts.toList(), stepImageAddNum)

                            if(isTempSuccess){ // tempost api 성공하면 savepost api  호출한다.
                                // savepost api success됐는지 확인하는 변수
                                val isSaveSuccess = recipeWriteViewModel.postSaveTempRecipe(tempId!!)

                                if (isSaveSuccess){ // post api 성공하면 업로드 완료 알럿을 띄운다.
                                    recipeWriteViewModel.onRecipeWriteDialogEvent(
                                        RecipeWriteDialogEvent.UploadCompleteChanged(true))
                                }
                            }
                            // 알럿 닫기
                            recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.UploadCategoryChanged(false))
                        }
                    }
                    // 업로드 된 레시피를 수정할 경우
                    else if(completeRecipeApiCalled > 0){
                        CoroutineScope(Dispatchers.Main).launch{
                            val isSuccess = recipeWriteViewModel.patchCompleteRecipe(recipeId!!, stepImageParts.toList(), stepImageAddNum)

                            if (isSuccess){
                                recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.EditCompleteChanged(true))
                            }
                        }
                        // 알럿 닫기
                        recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.UploadCategoryChanged(false))
                    }
                    // 처음 업로드할 경우
                    else {
                        CoroutineScope(Dispatchers.Main).launch {
                            // post api success됐는지 확인하는 변수
                            val isSuccess = recipeWriteViewModel.postRecipeWrite(stepImageParts = stepImageParts.toList())
                            Log.e("recipewrite-result", "레시피 api 성공 한 후 : ${isSuccess}")
                            // post api 성공하면 업로드 완료 알럿을 띄운다
                            if (isSuccess){
                                Log.e("recipewrite-result", "알럿 띄우기")
                                recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.UploadCompleteChanged(true))
                            }
                            // 알럿 닫기
                            recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.UploadCategoryChanged(false))
                        }
                    }
                },
                setShowDialog = {
                    recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.UploadCategoryChanged(it))
                }
            )
        }
        // 업로드 완료 알럿
        if (stateRecipeWriteDialog.isOpenUploadComplete){
            CustomDialogUploadComplete(
                image = stateThumbnail !!,
                setShowDialog = {
                    recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.UploadCompleteChanged(it))
                },
                onAccept = {
                    Log.e("recipewrite-result", "알럿 띄운 후 : ${stateUploadRecipeId}")
                    onClickViewRecipe(stateUploadRecipeId)
                },
                onLater = {
                    recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.UploadCompleteChanged(false))
                    onClickBack()
                }
            )
        }
        // 수정 완료 알럿
        if (stateRecipeWriteDialog.isOpenEditComplete){
            CustomDialogEditComplete(
                image = stateThumbnail !!,
                setShowDialog = {
                    recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.EditCompleteChanged(it))
                },
                onAccept = {
                    Log.e("recipewrite-result", "알럿 띄운 후 : ${stateEditRecipeId}")
                    onClickViewRecipe(stateEditRecipeId)
                },
                onLater = {
                    recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.EditCompleteChanged(false))
                    onClickNextTimeInEdit()
                }
            )
        }
        // 처음으로 작성 중, 취소 알럿
        if (stateRecipeWriteDialog.isOpenRecipeDelete) {
            CustomDialogRecipeDelete(
                setShowDialog = {
                    recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.RecipeDeleteChanged(it))
                },
                onDeleteClick = {
                    recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.RecipeDeleteChanged(false))
                    onClickBack()
                },
                onTemporalSave = {
                    CoroutineScope(Dispatchers.IO).launch {
                        recipeWriteViewModel.postRecipeWriteTemp(stepImageParts = stepImageParts.toList())
                    }
                    onClickBack()
                    recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.RecipeDeleteChanged(false))
                }
            )
        }
        // 임시저장 수정 중, 취소 알럿
        if(stateRecipeWriteDialog.isOpenTempRecipeDelete) {
            CustomDialogType1(
                title = "작성 중인 레시피 취소하기",
                text = "지금 돌아가면 작성 내용이 모두 삭제됩니다.\n" +
                        "마지막 임시 저장 상태로 돌아가시겠습니까?",
                declineText = "취소",
                acceptText = "레시피 작성 취소하기",
                setShowDialog = {
                    recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.TempRecipeDeleteChanged(it))
                },
                onAcceptClick = {
                    onClickBack()
                    recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.TempRecipeDeleteChanged(false))
                }
            )
        }
        // 업로드 수정 중, 취소 알럿
        if(stateRecipeWriteDialog.isOpenCompleteRecipeDelete){
            CustomDialogType1(
                title = "작성 중인 레시피 취소하기",
                text = "지금 돌아가면 작성 내용이 모두 삭제됩니다.\n" +
                        "마지막 업로드 상태로 돌아가시겠습니까?",
                declineText = "취소",
                acceptText = "마지막 상태로 돌아가기",
                setShowDialog = {
                    recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.CompleteRecipeDeleteChanged(it))
                },
                onAcceptClick = {
                    onClickBack()
                    recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.CompleteRecipeDeleteChanged(false))
                }
            )
        }
    }
}

