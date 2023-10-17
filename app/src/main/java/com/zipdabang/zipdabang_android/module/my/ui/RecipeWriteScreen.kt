package com.zipdabang.zipdabang_android.module.my.ui

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.my.ui.component.ButtonAddForIngredient
import com.zipdabang.zipdabang_android.module.my.ui.component.IngredientAndUnit
import com.zipdabang.zipdabang_android.module.my.ui.component.Step
import com.zipdabang.zipdabang_android.module.my.ui.state.recipewrite.RecipeWriteBeveragesEvent
import com.zipdabang.zipdabang_android.module.my.ui.state.recipewrite.RecipeWriteDialogEvent
import com.zipdabang.zipdabang_android.module.my.ui.state.recipewrite.RecipeWriteFormEvent
import com.zipdabang.zipdabang_android.module.my.ui.viewmodel.RecipeWriteViewModel
import com.zipdabang.zipdabang_android.ui.component.AppBarSignUp
import com.zipdabang.zipdabang_android.ui.component.CustomDialogCameraFile
import com.zipdabang.zipdabang_android.ui.component.CustomDialogRecipeDelete
import com.zipdabang.zipdabang_android.ui.component.CustomDialogSelectCategory
import com.zipdabang.zipdabang_android.ui.component.CustomDialogType1
import com.zipdabang.zipdabang_android.ui.component.CustomDialogType2
import com.zipdabang.zipdabang_android.ui.component.CustomDialogUploadComplete
import com.zipdabang.zipdabang_android.ui.component.ImageWithIconAndText
import com.zipdabang.zipdabang_android.ui.component.PrimaryButtonOutLined
import com.zipdabang.zipdabang_android.ui.component.PrimaryButtonOutLinedStatus
import com.zipdabang.zipdabang_android.ui.component.PrimaryButtonWithStatus
import com.zipdabang.zipdabang_android.ui.component.TextFieldForRecipeWriteMultiline
import com.zipdabang.zipdabang_android.ui.component.TextFieldForRecipeWriteSingleline
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
import java.io.InputStream


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun RecipeWriteScreen(
    onClickBack: () -> Unit,
    recipeWriteViewModel: RecipeWriteViewModel = hiltViewModel(),
    onClickViewRecipe: (Int) -> Unit
) {
    val stateRecipeWriteForm = recipeWriteViewModel.stateRecipeWriteForm
    val stateRecipeWriteDialog = recipeWriteViewModel.stateRecipeWriteDialog
    val stateRecipeWriteBeverages = recipeWriteViewModel.stateRecipeWriteBeverages
    val context = LocalContext.current
    val stateThumbnail = recipeWriteViewModel.stateRecipeWriteForm.thumbnail
    val stateUploadRecipeId = recipeWriteViewModel.uploadRecipeId // 기문이형 도와줘 ㅠㅠ
    Log.e("state-uploadrecipeid","${stateUploadRecipeId}")


    LaunchedEffect(key1 = stateRecipeWriteForm){
        recipeWriteViewModel.onRecipeWriteFormEvent(RecipeWriteFormEvent.BtnEnabled(true))
        recipeWriteViewModel.onRecipeWriteFormEvent(RecipeWriteFormEvent.BtnEnabledSave(true))
    }
    LaunchedEffect(key1 = stateRecipeWriteForm.ingredients) {
        recipeWriteViewModel.onRecipeWriteFormEvent(RecipeWriteFormEvent.BtnIngredientAddEnabled(stateRecipeWriteForm.ingredientsNum))
    }
    LaunchedEffect(key1= stateRecipeWriteForm.steps){
        recipeWriteViewModel.onRecipeWriteFormEvent(RecipeWriteFormEvent.StepIsValidate(stateRecipeWriteForm.stepsNum))
    }
    LaunchedEffect(key1 = stateRecipeWriteBeverages){
        recipeWriteViewModel.onRecipeWriteBeveragesEvent(RecipeWriteBeveragesEvent.BtnEnabled(true))
    }

    var thumbnailPhotoBitmap : Bitmap?
    var stepPhotoBitmap : Bitmap?
    val stepImageParts  = remember {
        mutableStateListOf<MultipartBody.Part>()
    }

    // thumbnail
    // 갤러리 -> Uri 형태
    val takeThumbnailFromAlbumLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                val file: File? = uri.path?.let { File(it) }

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

                recipeWriteViewModel.thumbnailPart = thumbnailPart
            } else {
                Log.e("Error in camera", "No image selected")
            }
        }

    // step
    // 갤러리 -> Uri 형태
    val takeStepPhotoFromAlbumLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                val file: File? = uri.path?.let { File(it) }

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

                stepImageParts.add(stepPart)
            } else {
                Log.e("Error in camera", "No image selected")
            }
        }
    // 카메라->Bitmap 형태
    /*val takePhotoFromCameraStepLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
            if (bitmap != null) {
                recipeWriteViewModel.onRecipeWriteFormEvent(RecipeWriteFormEvent.StepImageChanged(bitmap, stateRecipeWriteDialog.stepNum))

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
                recipeWriteViewModel.onRecipeWriteFormEvent(RecipeWriteFormEvent.StepImageChanged(compressedBitmap, stateRecipeWriteDialog.stepNum))




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
        }*/



    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
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
                    PrimaryButtonOutLinedStatus(
                        borderColor = ZipdabangandroidTheme.Colors.MainBackground,
                        text = stringResource(id = R.string.my_recipewrite_save),
                        onClick = {
                            recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.SaveChanged(true))
                        },
                        enabled = stateRecipeWriteForm.btnEnabledSave
                    )
                }
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    PrimaryButtonWithStatus(
                        isFormFilled = stateRecipeWriteForm.btnEnabled,
                        text = stringResource(id = R.string.my_recipewrite_writedone),
                        onClick = {
                            CoroutineScope(Dispatchers.Main).launch{
                                try{
                                    recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.UploadCategoryChanged(true))
                                } catch (e:Exception){}
                            }
                        },
                    )
                }

            }
        },
        topBar = {
            AppBarSignUp(
                navigationIcon = R.drawable.ic_topbar_backbtn,
                onClickNavIcon = {
                    if(!recipeWriteViewModel.isEmpty()){
                        recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.RecipeDeleteChanged(true))
                     } else{
                          onClickBack()
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
            //썸네일
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
                            ingredientNum = i+1,
                            valueIngredient = stateRecipeWriteForm.ingredients[i].ingredientName,
                            onValueChangedIngredient = { newText, maxLength ->
                                if (newText.length <= maxLength) {
                                    recipeWriteViewModel.onRecipeWriteFormEvent(RecipeWriteFormEvent.IngredientChanged(newText,i+1))
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
                                    recipeWriteViewModel.onRecipeWriteFormEvent(RecipeWriteFormEvent.QuantityChanged(newText,i+1))
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
                    if(stateRecipeWriteForm.ingredientsNum == 10){

                    } else {
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
                            stepNum = i+1,
                            stepImage = stateRecipeWriteForm.steps[i].stepImage,
                            value = stateRecipeWriteForm.steps[i].description,
                            valueLength = stateRecipeWriteForm.steps[i].stepWordCount.toString(),
                            onValueChanged = { newText, maxLength ->
                                if (newText.length <= maxLength) {
                                    recipeWriteViewModel.onRecipeWriteFormEvent(RecipeWriteFormEvent.StepChanged(newText, i+1))
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
                                recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.StepFileSelectChanged(true, i+1))
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
                        Text(
                            modifier = Modifier.padding(0.dp, 0.dp, 4.dp, 0.dp),
                            text = "Step" + stateRecipeWriteForm.stepsNum + "/Step10",
                            style = ZipdabangandroidTheme.Typography.fourteen_300,
                            color = ZipdabangandroidTheme.Colors.Typo
                        )
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
                        //takePhotoFromCameraLauncher.launch()
                        //recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.FileSelectChanged(false))
                    },
                    onFileClick = {
                        takeThumbnailFromAlbumLauncher.launch("image/*")
                        recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.FileSelectChanged(false))
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
                        //takePhotoFromCameraStepLauncher.launch()
                        //recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.StepFileSelectChanged(false, stateRecipeWriteDialog.stepNum))
                    },
                    onFileClick = {
                        takeStepPhotoFromAlbumLauncher.launch("image/*")
                        recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.StepFileSelectChanged(false, stateRecipeWriteDialog.stepNum))
                    }
                )
            }
            // 임시저장 알럿
            if (stateRecipeWriteDialog.isOpenSave) {
                CustomDialogType2(
                    title = stringResource(id = R.string.my_save),
                    text = stringResource(id = R.string.my_dialog_save_detail),
                    declineText = stringResource(id = R.string.my_dialog_cancel),
                    acceptText = stringResource(id = R.string.my_save),
                    setShowDialog = {
                        recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.SaveChanged(it))
                    },
                    onAcceptClick = {
                        // 임시저장 api
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
                        CoroutineScope(Dispatchers.Main).launch {
                            // post api success됐는지 확인하는 변수
                            val isSuccess = recipeWriteViewModel.postRecipeWrite(stepImageParts = stepImageParts.toList())
                            // post api 성공하면 업로드 완료 알럿을 띄운다
                            if (isSuccess){
                                recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.UploadCompleteChanged(true))
                            }
                            recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.UploadCategoryChanged(false))
                        }
                    },
                    setShowDialog = {
                        recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.UploadCategoryChanged(it))
                    }
                )
            }
            // 업로드 알럿
            if (stateRecipeWriteDialog.isOpenUploadComplete){
                CustomDialogUploadComplete(
                    image = stateThumbnail !!,
                    setShowDialog = {
                        recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.UploadCompleteChanged(it))
                    },
                    onAccept = {
                        onClickViewRecipe(stateUploadRecipeId)
                    },
                    onLater = {
                        recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.UploadCompleteChanged(false))
                        onClickBack()
                    }
                )
            }
            // 작성 중 취소 알럿
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
                        // 임시저장 api & navGraph 이동
                        recipeWriteViewModel.onRecipeWriteDialogEvent(RecipeWriteDialogEvent.RecipeDeleteChanged(false))
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
        onClickBack = {},
        onClickViewRecipe = { recipeId -> }
    )
}