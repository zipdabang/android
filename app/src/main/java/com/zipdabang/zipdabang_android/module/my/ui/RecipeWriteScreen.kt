package com.zipdabang.zipdabang_android.module.my.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.my.ui.component.ButtonForIngredient
import com.zipdabang.zipdabang_android.module.my.ui.component.ButtonForStep
import com.zipdabang.zipdabang_android.module.my.ui.component.IngredientAndUnit
import com.zipdabang.zipdabang_android.module.my.ui.component.Step
import com.zipdabang.zipdabang_android.module.my.ui.state.recipewrite.RecipeWriteFormEvent
import com.zipdabang.zipdabang_android.module.my.ui.viewmodel.RecipeWriteViewModel
import com.zipdabang.zipdabang_android.ui.component.AppBarSignUp
import com.zipdabang.zipdabang_android.ui.component.CustomDialogCameraFile
import com.zipdabang.zipdabang_android.ui.component.CustomDialogRecipeDelete
import com.zipdabang.zipdabang_android.ui.component.CustomDialogType1
import com.zipdabang.zipdabang_android.ui.component.CustomDialogType2
import com.zipdabang.zipdabang_android.ui.component.ImageWithIconAndText
import com.zipdabang.zipdabang_android.ui.component.PrimaryButton
import com.zipdabang.zipdabang_android.ui.component.PrimaryButtonWithStatus
import com.zipdabang.zipdabang_android.ui.component.TextFieldForRecipeWriteMultiline
import com.zipdabang.zipdabang_android.ui.component.TextFieldForRecipeWriteSingleline
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import java.io.ByteArrayOutputStream


// Bitmap을 Uri로 변환하는 함수
fun bitmapToUri(context: Context, bitmap: Bitmap): Uri? {
    val bytes = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 0, bytes)
    val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Image", null)
    return Uri.parse(path)
}

@Composable
fun RecipeWriteScreen(
    onClickBack: () -> Unit,
    recipeWriteViewModel: RecipeWriteViewModel = hiltViewModel()
) {
    val stateRecipeWriteForm = recipeWriteViewModel.stateRecipeWriteForm
    val stateRecipeWriteDialog = recipeWriteViewModel.stateRecipeWriteDialog

    LaunchedEffect(key1 = stateRecipeWriteForm.ingredientsNum) {

    }
    // textfield
    var textStateIngredient by remember { mutableStateOf("") }
    var textStateUnit by remember { mutableStateOf("") }
    var textStateStep by remember { mutableStateOf("") }

    // 알럿
    val showDialogRecipeDelete = remember { mutableStateOf(false) }
    val showDialogFileSelect = remember { mutableStateOf(false) }
    val showDialogPerimission = remember { mutableStateOf(false) }
    val showDialogSave = remember { mutableStateOf(false) }
    val showDialogUpload = remember { mutableStateOf(false) }
    val showDialogUploadComplete = remember { mutableStateOf(false) }


    // 사진
    var thumbnail by remember { mutableStateOf<Any?>(null) }
    val context = LocalContext.current
    // 갤러리->Uri 형태
    val takePhotoFromAlbumLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                thumbnail = uri
                val inputStream = context.contentResolver.openInputStream(uri)

                if (inputStream != null) {
                    // 파일 크기를 바이트로 계산
                    val fileSizeInBytes = inputStream.available().toLong()

                    // 바이트를 메가바이트(MB)로 변환
                    val fileSizeInMB = fileSizeInBytes.toDouble() / (1024 * 1024)

                    val bitmap = BitmapFactory.decodeStream(inputStream)

                    // 이미지를 JPEG 형식으로 압축
                    val quality = 100 // JPEG 압축 품질 (0-100)
                    val outputStream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)

                    // 압축된 이미지 바이트 배열
                    val compressedImageByteArray = outputStream.toByteArray()
                    val compressedImageSizeInMB =
                        compressedImageByteArray.size.toDouble() / (1024 * 1024)
                    Log.e("사진-앨범 압축 전", "$uri, $fileSizeInMB MB")
                    Log.e(
                        "사진-앨범 압축 후",
                        "${compressedImageByteArray}, ${compressedImageSizeInMB} MB"
                    )

                    // 압축된 이미지를 파일로 저장하거나 서버에 업로드할 수 있습니다.

                    // 예를 들어, 압축된 이미지 바이트 배열을 파일로 저장하려면:
                    // val file = File(context.cacheDir, "compressed_image.jpg")
                    // FileOutputStream(file).use { it.write(compressedImageByteArray) }

                    // 예를 들어, 압축된 이미지 바이트 배열을 서버로 업로드하려면:
                    // uploadImageToServer(compressedImageByteArray)

                    // InputStream을 닫습니다.
                    inputStream.close()
                } else {
                    Log.e("사진-앨범", "Uri를 열 수 없음")
                    thumbnail = uri
                }
            }
        }
    // 카메라->Bitmap 형태
    val takePhotoFromCameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
            if (bitmap != null) {
                thumbnail = bitmap

                val baos = ByteArrayOutputStream()
                bitmap.compress(
                    Bitmap.CompressFormat.PNG,
                    100,
                    baos
                )

                // ByteArrayOutputStream을 ByteArray로 변환
                val byteArray = baos.toByteArray()

                // ByteArray를 다시 Bitmap으로 변환
                val compressedBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)

                // compressedBitmap을 thumbnail 변수에 할당
                thumbnail = compressedBitmap





                // 비트맵의 너비와 높이를 가져옵니다.
                val width = bitmap.width
                val height = bitmap.height

                // 비트맵의 Config를 가져옵니다.
                val config = bitmap.config ?: Bitmap.Config.ARGB_8888

                // 비트맵의 비트 수를 계산합니다.
                val bitsPerPixel = when (config) {
                    Bitmap.Config.ALPHA_8 -> 8
                    Bitmap.Config.RGB_565 -> 16
                    else -> 32 // 기본적으로 ARGB_8888을 사용
                }

                val totalBits = width * height * bitsPerPixel

                // 비트를 바이트로 변환하고, 메가바이트로 변환합니다.
                val totalBytes = totalBits / 8
                val totalMegabytes = totalBytes.toDouble() / (1024 * 1024)

                // 로그로 비트맵 크기를 출력합니다.
                Log.e("사진-카메라 압축 전", "${bitmap}, ${totalMegabytes} MB")


                val quality = 100

                // ByteArrayOutputStream을 사용하여 Bitmap을 JPEG으로 압축합니다.
                val outputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)

                // ByteArrayOutputStream을 ByteArray로 변환합니다.
                val compressedImageByteArray = outputStream.toByteArray()
                val compressedImageSizeInMB =
                    compressedImageByteArray.size.toDouble() / (1024 * 1024)

                // 이제 압축된 이미지 바이트 배열을 서버에 업로드하거나 저장할 수 있습니다.

                // 예를 들어, 압축된 이미지 바이트 배열을 서버로 업로드하려면 다음과 같이 할 수 있습니다:
                // uploadImageToServer(compressedImageByteArray)

                // Bitmap을 띄울 때 사용할 수 있도록 이미지를 갱신합니다.
                // thumbnailUri = bitmapToUri(context, bitmap)

                Log.e("사진-카메라 압축 후", "${compressedImageByteArray}, ${compressedImageSizeInMB} MB")


                // Bitmap -> jpeg로 변환해서 서버에 전송해야함
                // 띄울때는 Bitmap 형태로 사진 띄우기
            }
        }




    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            AppBarSignUp(
                navigationIcon = R.drawable.ic_topbar_backbtn,
                onClickNavIcon = {
                    // 만약 하나라도 차있으면, dialog 띄운 후에 onClickBack()
                    // if(){
                    showDialogRecipeDelete.value = true
                    // } else{
                    //      onClickBack()
                    // }
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
            //썸네일
            Box(
                modifier = Modifier
                    .height(360.dp)
                    .fillMaxWidth()
                    .aspectRatio(1f)
            ) {
                ImageWithIconAndText(
                    addImageClick = {
                        showDialogFileSelect.value = true
                    },
                    deleteImageClick = {
                        thumbnail = null
                    },
                    imageUrl = thumbnail,
                    iconImageVector = R.drawable.ic_recipewrite_camera,
                    iconTint = ZipdabangandroidTheme.Colors.Typo.copy(0.5f),
                    iconModifier = Modifier.size(27.dp, 24.dp),
                    text = stringResource(id = R.string.my_recipewrite_thumbnail_upload),
                    textStyle = ZipdabangandroidTheme.Typography.sixteen_700,
                    textColor = ZipdabangandroidTheme.Colors.Typo.copy(0.5f)
                )
            }

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
                        color = ZipdabangandroidTheme.Colors.Choco
                    )
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
                    Text(
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(0.dp, 0.dp, 4.dp, 0.dp),
                        text = stateRecipeWriteForm.titleWordCount.toString() + "/20",
                        style = ZipdabangandroidTheme.Typography.fourteen_300,
                        color = ZipdabangandroidTheme.Colors.Typo
                    )
                }

                // 소요 시간
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.my_recipewrite_time),
                        style = ZipdabangandroidTheme.Typography.sixteen_700,
                        color = ZipdabangandroidTheme.Colors.Choco
                    )
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
                    Text(
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(0.dp, 0.dp, 4.dp, 0.dp),
                        text = stateRecipeWriteForm.timeWordCount.toString() + "/20",
                        style = ZipdabangandroidTheme.Typography.fourteen_300,
                        color = ZipdabangandroidTheme.Colors.Typo
                    )
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
                    Text(
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(0.dp, 0.dp, 4.dp, 0.dp),
                        text = stateRecipeWriteForm.introWordCount.toString() + "/100",
                        style = ZipdabangandroidTheme.Typography.fourteen_300,
                        color = ZipdabangandroidTheme.Colors.Typo
                    )
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
                    for (i in 0 until stateRecipeWriteForm.ingredientsNum) {
                        IngredientAndUnit(
                            valueIngredient = stateRecipeWriteForm.ingredients[i].ingredientName,
                            onValueChangedIngredient = { newText, maxLength ->
                                if (newText.length <= maxLength) {
                                    textStateIngredient = newText
                                }
                            },
                            placeholderValueIngredient = stringResource(id = R.string.my_recipewrite_milk),
                            maxLengthIngredient = 16,
                            imeActionIngredient = ImeAction.Default,
                            onClickTrailingiconIngredient = {
                                textStateIngredient = ""
                            },
                            onClickCancelIngredient = {

                            },
                            valueUnit = stateRecipeWriteForm.ingredients[i].quantity,
                            onValueChangedUnit = { newText, maxLength ->
                                if (newText.length <= maxLength) {
                                    textStateUnit = newText
                                }
                            },
                            placeholderValueUnit = stringResource(id = R.string.my_recipewrite_hundredmilli),
                            maxLengthUnit = 16,
                            imeActionUnit = ImeAction.Default,
                            onClickTrailingiconUnit = {
                                textStateUnit = ""
                            }
                        )
                    }
                    ButtonForIngredient(
                        borderColor = ZipdabangandroidTheme.Colors.Strawberry,
                        containerColor = Color.White,
                        enabled = true,
                        onClickBtn = {
                            recipeWriteViewModel.onRecipeWriteFormEvent(
                                RecipeWriteFormEvent.BtnIngredientAdd(
                                    ingredientNum = stateRecipeWriteForm.ingredientsNum + 1
                                )
                            )
                        }
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 0.dp, 0.dp, 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
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
                        Text(
                            modifier = Modifier.padding(0.dp, 0.dp, 4.dp, 0.dp),
                            text = stateRecipeWriteForm.ingredientsNum.toString() + "/10",
                            style = ZipdabangandroidTheme.Typography.fourteen_300,
                            color = ZipdabangandroidTheme.Colors.Typo
                        )
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
                    for (i in 0 until stateRecipeWriteForm.stepsNum) {
                        Step(
                            stepNum = stateRecipeWriteForm.stepsNum,
                            value = textStateStep,
                            onValueChanged = { newText, maxLength ->
                                if (newText.length <= maxLength) {
                                    textStateStep = newText
                                }
                            },
                            placeholderValue = stringResource(id = R.string.my_recipewrite_step_hint),
                            height = 232.dp,
                            maxLines = 7,
                            maxLength = 200,
                            imeAction = ImeAction.None,
                            onClickImageAddBtn = { },
                            onClickDeleteStep = { },
                            onClickEditStep = { }
                        )
                    }
                    ButtonForStep(
                        borderColor = ZipdabangandroidTheme.Colors.Strawberry,
                        containerColor = Color.White,
                        enabled = true,
                        onClickBtn = {
                            recipeWriteViewModel.onRecipeWriteFormEvent(
                                RecipeWriteFormEvent.BtnStepAdd(
                                    stepNum = stateRecipeWriteForm.stepsNum + 1
                                )
                            )
                        }
                    )
                    Text(
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(0.dp, 0.dp, 4.dp, 0.dp),
                        text = "Step" + stateRecipeWriteForm.stepsNum + "/Step10",
                        style = ZipdabangandroidTheme.Typography.fourteen_300,
                        color = ZipdabangandroidTheme.Colors.Typo
                    )
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


            // 하단 버튼
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
                    PrimaryButton(
                        backgroundColor = ZipdabangandroidTheme.Colors.MainBackground,
                        text = stringResource(id = R.string.my_recipewrite_save),
                        onClick = {
                            showDialogSave.value = true
                        },
                    )
                }
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    PrimaryButtonWithStatus(
                        isFormFilled = false,
                        text = stringResource(id = R.string.my_recipewrite_writedone),
                        onClick = {},
                    )
                }

            }


            // 알럿
            // 권한허용 알럿
            if (showDialogPerimission.value) {
                CustomDialogType1(
                    title = stringResource(id = R.string.my_dialog_permission),
                    text = stringResource(id = R.string.my_dialog_permission_detail),
                    declineText = stringResource(id = R.string.my_dialog_cancel),
                    acceptText = stringResource(id = R.string.my_dialog_permit),
                    setShowDialog = {
                        showDialogPerimission.value = it
                    },
                    onAcceptClick = {
                        // 카메라 허용받기
                        showDialogPerimission.value = false
                    }
                )
            }
            // 파일선택 알럿
            if (showDialogFileSelect.value) {
                CustomDialogCameraFile(
                    title = stringResource(id = R.string.my_dialog_fileselect),
                    setShowDialog = {
                        showDialogFileSelect.value = it
                    },
                    onCameraClick = {
                        takePhotoFromCameraLauncher.launch()
                        showDialogFileSelect.value = false
                    },
                    onFileClick = {
                        takePhotoFromAlbumLauncher.launch("image/*")
                        showDialogFileSelect.value = false
                    }
                )
            }
            // 임시저장 알럿
            if (showDialogSave.value) {
                CustomDialogType2(
                    title = stringResource(id = R.string.my_save),
                    text = stringResource(id = R.string.my_dialog_save_detail),
                    declineText = stringResource(id = R.string.my_dialog_cancel),
                    acceptText = stringResource(id = R.string.my_save),
                    setShowDialog = {
                        showDialogSave.value = it
                    },
                    onAcceptClick = {
                        // 임시저장 api & navGraph 이동
                        showDialogSave.value = false
                    }
                )
            }
            // 업로드 알럿
            if (showDialogUpload.value) {
                /*CustomDialogSelectCategory(
                    categoryList = ,
                    categoryParagraphList = ,
                    categorySelectedList = ,
                    onSelectClick = {},
                    onCompleteClick = { *//*TODO*//* },
                    setShowDialog = {}
                )*/
            }
            // 작성 중 취소 알럿
            if (showDialogRecipeDelete.value) {
                CustomDialogRecipeDelete(
                    setShowDialog = {
                        showDialogRecipeDelete.value = it
                    },
                    onDeleteClick = {
                        showDialogRecipeDelete.value = false
                        onClickBack()
                    },
                    onTemporalSave = {
                        // 임시저장 api & navGraph 이동
                        showDialogRecipeDelete.value = false
                    }
                )
            }
        }
    }
}


@Preview
@Composable
fun PreviewRecipeWriteScreen() {
    RecipeWriteScreen(
        onClickBack = {}
    )
}