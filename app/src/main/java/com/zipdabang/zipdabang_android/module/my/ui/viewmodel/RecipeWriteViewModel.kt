package com.zipdabang.zipdabang_android.module.my.ui.viewmodel

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.my.data.remote.recipewrite.RecipeWriteContent
import com.zipdabang.zipdabang_android.module.my.data.remote.recipewrite.RecipeWriteIngredient
import com.zipdabang.zipdabang_android.module.my.data.remote.recipewrite.RecipeWriteStep
import com.zipdabang.zipdabang_android.module.my.data.remote.recipewrite.RecipeWriteTempContent
import com.zipdabang.zipdabang_android.module.my.data.remote.recipewrite.RecipeWriteTempIngredient
import com.zipdabang.zipdabang_android.module.my.data.remote.recipewrite.RecipeWriteTempStep
import com.zipdabang.zipdabang_android.module.my.data.remote.recipewrite.TempRecipeWriteContent
import com.zipdabang.zipdabang_android.module.my.data.remote.recipewrite.TempRecipeWriteIngredient
import com.zipdabang.zipdabang_android.module.my.data.remote.recipewrite.TempRecipeWriteStep
import com.zipdabang.zipdabang_android.module.my.domain.usecase.GetRecipeWriteBeveragesUseCase
import com.zipdabang.zipdabang_android.module.my.domain.usecase.GetTempRecipeDetailUseCase
import com.zipdabang.zipdabang_android.module.my.domain.usecase.PostAsPatchTempRecipeUseCase
import com.zipdabang.zipdabang_android.module.my.domain.usecase.PostRecipeWriteTempUseCase
import com.zipdabang.zipdabang_android.module.my.domain.usecase.PostRecipeWriteUseCase
import com.zipdabang.zipdabang_android.module.my.domain.usecase.PostSaveTempRecipeUseCase
import com.zipdabang.zipdabang_android.module.my.ui.state.recipewrite.Ingredient
import com.zipdabang.zipdabang_android.module.my.ui.state.recipewrite.RecipeWriteBeveragesEvent
import com.zipdabang.zipdabang_android.module.my.ui.state.recipewrite.RecipeWriteBeveragesState
import com.zipdabang.zipdabang_android.module.my.ui.state.recipewrite.RecipeWriteDialogEvent
import com.zipdabang.zipdabang_android.module.my.ui.state.recipewrite.RecipeWriteDialogState
import com.zipdabang.zipdabang_android.module.my.ui.state.recipewrite.RecipeWriteFormEvent
import com.zipdabang.zipdabang_android.module.my.ui.state.recipewrite.RecipeWriteFormState
import com.zipdabang.zipdabang_android.module.my.ui.state.recipewrite.Step
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class RecipeWriteViewModel @Inject constructor(
    private val dataStore: DataStore<Token>,
    private val postRecipeWriteUseCase: PostRecipeWriteUseCase,
    private val getRecipeWriteBeveragesUseCase: GetRecipeWriteBeveragesUseCase,
    private val postRecipeWriteTempUseCase: PostRecipeWriteTempUseCase,
    private val getTempRecipeDetailUseCase: GetTempRecipeDetailUseCase,
    private val postAsPatchTempRecipeUseCase: PostAsPatchTempRecipeUseCase,
    private val postSaveTempRecipeUseCase: PostSaveTempRecipeUseCase,
) : ViewModel() {

    var stateRecipeWriteForm by mutableStateOf(
        RecipeWriteFormState(
            ingredients = listOf(
                Ingredient(
                    ingredientName = "",
                    quantity = "",
                )
            ),
            steps = listOf(
                Step(
                    stepImage = null,
                    description = "",
                    stepWordCount = 0,
                    completeBtnEnabled = false,
                    completeBtnVisible = true,
                    addBtnVisible = false,
                )
            ),
        ),
    )
    var stateTempRecipeWriteForm by mutableStateOf(
        RecipeWriteFormState(
            ingredients = listOf(
                Ingredient(
                    ingredientName = "",
                    quantity = "",
                )
            ),
            steps = listOf(
                Step(
                    stepImage = null,
                    description = "",
                    stepWordCount = 0,
                    completeBtnEnabled = false,
                    completeBtnVisible = true,
                    addBtnVisible = false,
                )
            ),
        ),
    )
    var stateRecipeWriteDialog by mutableStateOf(RecipeWriteDialogState())
    var thumbnailPart by mutableStateOf<MultipartBody.Part?>(null)
    var stateRecipeWriteBeverages by mutableStateOf(RecipeWriteBeveragesState())
    var uploadRecipeId by mutableStateOf(0) //기문이형 도와줘
    var tempRecipeDetailApiCalled by mutableStateOf(0)


    // stateRecipeWriteForm 비어있는지 확인하는 함수
    fun isEmpty(): Boolean {
        val isEmpty = listOf(
            stateRecipeWriteForm.ingredients[0].ingredientName.isEmpty()
                    && stateRecipeWriteForm.ingredients[0].quantity.isEmpty()
                    && (stateRecipeWriteForm.ingredientsNum == 1),
            stateRecipeWriteForm.steps[0].stepImage == null
                    && stateRecipeWriteForm.steps[0].description.isEmpty()
                    && (stateRecipeWriteForm.stepsNum == 1),
            stateRecipeWriteForm.recipeTip.isEmpty(),
            stateRecipeWriteForm.intro.isEmpty(),
            stateRecipeWriteForm.time.isEmpty(),
            stateRecipeWriteForm.title.isEmpty(),
            (stateRecipeWriteForm.thumbnail == null) || (stateRecipeWriteForm.thumbnail == "")
        ).all { it }

        return isEmpty
    }

    // stateRecipeWriteForm과 stateTempRecipeWriteForm의 차이점을 확인하는 함수
    fun isTempRecipeEdited(): Boolean {
        Log.e("뭐지 stateRecipeWriteForm", "${stateRecipeWriteForm}")
        Log.e("뭐지 stateTempRecipeWriteForm", "${stateTempRecipeWriteForm}")
        Log.e("뭐지", "${stateRecipeWriteForm != stateTempRecipeWriteForm}")
        return stateRecipeWriteForm != stateTempRecipeWriteForm
    }

    // stateRecipeWriteForm에 대한 event
    fun onRecipeWriteFormEvent(event: RecipeWriteFormEvent) {
        when (event) {
            is RecipeWriteFormEvent.TitleChanged -> {
                stateRecipeWriteForm = stateRecipeWriteForm.copy(
                    title = event.title,
                    titleWordCount = event.title.length
                )
            }

            is RecipeWriteFormEvent.TimeChanged -> {
                stateRecipeWriteForm = stateRecipeWriteForm.copy(
                    time = event.time,
                    timeWordCount = event.time.length
                )
            }

            is RecipeWriteFormEvent.IntroChanged -> {
                stateRecipeWriteForm = stateRecipeWriteForm.copy(
                    intro = event.intro,
                    introWordCount = event.intro.length
                )
            }

            is RecipeWriteFormEvent.RecipeTipChanged -> {
                stateRecipeWriteForm = stateRecipeWriteForm.copy(
                    recipeTip = event.recipeTip,
                    recipeTipWordCount = event.recipeTip.length
                )
            }

            // 썸네일
            is RecipeWriteFormEvent.ThumbnailChanged -> {
                stateRecipeWriteForm = stateRecipeWriteForm.copy(
                    thumbnail = event.thumbnail
                )
            }

            is RecipeWriteFormEvent.ThumbnailChangedToNull -> {
                stateRecipeWriteForm = stateRecipeWriteForm.copy(
                    thumbnail = null
                )
            }


            // 재료
            is RecipeWriteFormEvent.BtnIngredientAdd -> {
                val currentIngredients = stateRecipeWriteForm.ingredients.toMutableList()
                val newIngredient = Ingredient(
                    ingredientName = "",
                    quantity = "",
                )
                if (currentIngredients.size < 10) {
                    currentIngredients.add(newIngredient)
                    stateRecipeWriteForm = stateRecipeWriteForm.copy(
                        ingredients = currentIngredients,
                        ingredientsNum = currentIngredients.size
                    )
                }
            }

            is RecipeWriteFormEvent.BtnIngredientDelete -> {
                val currentIngredients = stateRecipeWriteForm.ingredients.toMutableList()
                currentIngredients.removeAt(event.ingredientNum - 1)

                stateRecipeWriteForm = stateRecipeWriteForm.copy(
                    ingredients = currentIngredients,
                    ingredientsNum = currentIngredients.size
                )
            }

            is RecipeWriteFormEvent.IngredientChanged -> {
                val ingredientNumToChange = event.ingredientNum
                val newIngredientName = event.ingredientName

                val currentIngredients = stateRecipeWriteForm.ingredients.toMutableList()
                val updatedIngredient = currentIngredients[ingredientNumToChange - 1].copy(
                    ingredientName = newIngredientName,
                )

                currentIngredients[ingredientNumToChange - 1] = updatedIngredient
                stateRecipeWriteForm = stateRecipeWriteForm.copy(
                    ingredients = currentIngredients
                )
            }

            is RecipeWriteFormEvent.QuantityChanged -> {
                val quantityNumToChange = event.quantityNum
                val newQuantityName = event.quantityName

                val currentIngredients = stateRecipeWriteForm.ingredients.toMutableList()
                val updatedIngredient = currentIngredients[quantityNumToChange - 1].copy(
                    quantity = newQuantityName
                )

                currentIngredients[quantityNumToChange - 1] = updatedIngredient
                stateRecipeWriteForm = stateRecipeWriteForm.copy(
                    ingredients = currentIngredients
                )
            }

            is RecipeWriteFormEvent.BtnIngredientAddEnabled -> {
                var allIngredientsValid = true // 모든 재료가 유효한지 여부를 저장할 변수

                for (i in 0 until event.ingredientNum) {
                    if (stateRecipeWriteForm.ingredients[i].ingredientName.isEmpty() || stateRecipeWriteForm.ingredients[i].quantity.isEmpty()) {
                        allIngredientsValid = false
                        break
                    }
                }

                stateRecipeWriteForm = stateRecipeWriteForm.copy(
                    ingredientBtnEnabled = allIngredientsValid
                )
            }


            // 스텝
            is RecipeWriteFormEvent.BtnStepAdd -> {
                val stepNumToChange = event.stepNum
                val currentSteps = stateRecipeWriteForm.steps.toMutableList()
                val preStep = currentSteps[stepNumToChange - 1].copy(
                    addBtnVisible = false,
                )
                val newStep = Step(
                    stepImage = null,
                    description = "",
                    stepWordCount = 0,
                    completeBtnEnabled = false,
                    completeBtnVisible = true,
                    addBtnVisible = false,
                )

                currentSteps[stepNumToChange - 1] = preStep
                if (stepNumToChange <= 10) {
                    currentSteps.add(newStep)
                }

                stateRecipeWriteForm = stateRecipeWriteForm.copy(
                    steps = currentSteps,
                    stepsNum = currentSteps.size
                )
            }

            is RecipeWriteFormEvent.BtnStepComplete -> {
                val stepNumToChange = event.stepNum
                val currentSteps = stateRecipeWriteForm.steps.toMutableList()
                var presentStep: Step

                if (currentSteps.size == 10) {
                    presentStep = currentSteps[stepNumToChange - 1].copy(
                        completeBtnVisible = false,
                        addBtnVisible = false
                    )
                } else if (currentSteps.size == stepNumToChange) {
                    presentStep = currentSteps[stepNumToChange - 1].copy(
                        completeBtnVisible = false,
                        addBtnVisible = true,
                    )
                } else {
                    presentStep = currentSteps[stepNumToChange - 1].copy(
                        completeBtnVisible = false,
                        addBtnVisible = false,
                    )
                }

                currentSteps[stepNumToChange - 1] = presentStep
                stateRecipeWriteForm = stateRecipeWriteForm.copy(
                    steps = currentSteps,
                )
            }

            is RecipeWriteFormEvent.BtnStepDelete -> {
                val stepNumToDelete = event.stepNum
                val currentSteps = stateRecipeWriteForm.steps.toMutableList()

                if (stepNumToDelete > 1) {
                    val preStep = currentSteps[stepNumToDelete - 2].copy(
                        addBtnVisible = true,
                    )
                    currentSteps[stepNumToDelete - 2] = preStep
                    currentSteps.removeAt(stepNumToDelete - 1)
                    stateRecipeWriteForm = stateRecipeWriteForm.copy(
                        steps = currentSteps,
                        stepsNum = currentSteps.size
                    )
                } else if (stepNumToDelete == 1) {
                }
            }

            is RecipeWriteFormEvent.BtnStepEdit -> {
                val stepNumToEdit = event.stepNum
                val currentSteps = stateRecipeWriteForm.steps.toMutableList()

                val editStep = currentSteps[stepNumToEdit - 1].copy(
                    completeBtnVisible = true,
                    completeBtnEnabled = true,
                    addBtnVisible = false,
                )
                currentSteps[stepNumToEdit - 1] = editStep

                stateRecipeWriteForm = stateRecipeWriteForm.copy(
                    steps = currentSteps,
                    stepsNum = currentSteps.size
                )
            }

            is RecipeWriteFormEvent.StepChanged -> {
                val stepNumToChange = event.stepNum
                val newStepDescription = event.stepDescription

                val currentSteps = stateRecipeWriteForm.steps.toMutableList()
                val updatedStep = currentSteps[stepNumToChange - 1].copy(
                    description = newStepDescription,
                    stepWordCount = newStepDescription.length
                )
                Log.e("recipewriteform", "${updatedStep}")
                currentSteps[stepNumToChange - 1] = updatedStep
                stateRecipeWriteForm = stateRecipeWriteForm.copy(
                    steps = currentSteps
                )
            }

            is RecipeWriteFormEvent.StepImageChanged -> {
                val stepNumToChange = event.stepNum
                val newStepImage = event.stepImage

                val currentSteps = stateRecipeWriteForm.steps.toMutableList()
                val updatedStep = currentSteps[stepNumToChange - 1].copy(
                    stepImage = newStepImage
                )

                currentSteps[stepNumToChange - 1] = updatedStep
                stateRecipeWriteForm = stateRecipeWriteForm.copy(
                    steps = currentSteps
                )
            }

            is RecipeWriteFormEvent.StepImageChangedToEmpty -> {
                val stepNumToChange = event.stepNum
                val newStepImage = event.stepImage

                val currentSteps = stateRecipeWriteForm.steps.toMutableList()
                val updatedStep = currentSteps[stepNumToChange - 1].copy(
                    stepImage = newStepImage
                )

                currentSteps[stepNumToChange - 1] = updatedStep
                stateRecipeWriteForm = stateRecipeWriteForm.copy(
                    steps = currentSteps
                )
            }

            is RecipeWriteFormEvent.StepIsValidate -> {
                val stepNumToChange = event.stepNum

                val currentSteps = stateRecipeWriteForm.steps.toMutableList()

                // 스텝의 유효성을 개별적으로 확인
                val stepIsValid =
                    stateRecipeWriteForm.steps[stepNumToChange - 1].description.isNotEmpty()
                            && stateRecipeWriteForm.steps[stepNumToChange - 1].stepImage != null

                // 해당 스텝의 completeBtnEnabled 업데이트
                val updatedStep = currentSteps[stepNumToChange - 1].copy(
                    completeBtnEnabled = stepIsValid
                )

                currentSteps[stepNumToChange - 1] = updatedStep

                stateRecipeWriteForm = stateRecipeWriteForm.copy(
                    steps = currentSteps
                )
            }

            is RecipeWriteFormEvent.BtnEnabled -> {
                val isAllFieldsFilled =
                    stateRecipeWriteForm.title.isNotBlank()
                            && !(stateRecipeWriteForm.thumbnail == null || stateRecipeWriteForm.thumbnail == "")
                            && stateRecipeWriteForm.time.isNotBlank()
                            && stateRecipeWriteForm.intro.isNotBlank()
                            && stateRecipeWriteForm.recipeTip.isNotBlank()
                            && stateRecipeWriteForm.ingredients.all { it.ingredientName.isNotBlank() && it.quantity.isNotBlank() }
                            && stateRecipeWriteForm.steps.all { it.description.isNotBlank() && it.stepImage != null }
                            && stateRecipeWriteForm.steps.all { !it.completeBtnVisible }

                stateRecipeWriteForm = stateRecipeWriteForm.copy(
                    btnEnabled = isAllFieldsFilled
                )
            }

            is RecipeWriteFormEvent.BtnEnabledSave -> {
                val isEmpty = listOf(
                    stateRecipeWriteForm.ingredients[0].ingredientName.isEmpty()
                            && stateRecipeWriteForm.ingredients[0].quantity.isEmpty()
                            && (stateRecipeWriteForm.ingredientsNum == 1),
                    stateRecipeWriteForm.steps[0].stepImage == null
                            && stateRecipeWriteForm.steps[0].description.isEmpty()
                            && (stateRecipeWriteForm.stepsNum == 1),
                    stateRecipeWriteForm.recipeTip.isEmpty(),
                    stateRecipeWriteForm.intro.isEmpty(),
                    stateRecipeWriteForm.time.isEmpty(),
                    stateRecipeWriteForm.title.isEmpty(),
                    (stateRecipeWriteForm.thumbnail == null) || (stateRecipeWriteForm.thumbnail == "")
                ).any { it == false }

                stateRecipeWriteForm = stateRecipeWriteForm.copy(
                    btnEnabledSave = isEmpty
                )
            }
        }
    }

    // stateRecipeWriteDialog에 대한 event
    fun onRecipeWriteDialogEvent(event: RecipeWriteDialogEvent) {
        when (event) {
            is RecipeWriteDialogEvent.RecipeDeleteChanged -> {
                stateRecipeWriteDialog =
                    stateRecipeWriteDialog.copy(isOpenRecipeDelete = event.isOpen)
            }

            is RecipeWriteDialogEvent.FileSelectChanged -> {
                stateRecipeWriteDialog =
                    stateRecipeWriteDialog.copy(isOpenFileSelect = event.isOpen)
            }

            is RecipeWriteDialogEvent.StepFileSelectChanged -> {
                if (!stateRecipeWriteForm.steps[event.stepNum - 1].completeBtnVisible) {

                } else {
                    stateRecipeWriteDialog = stateRecipeWriteDialog.copy(
                        isOpenStepFileSelect = event.isOpen,
                        stepNum = event.stepNum
                    )
                }
            }

            is RecipeWriteDialogEvent.PermissionChanged -> {
                stateRecipeWriteDialog =
                    stateRecipeWriteDialog.copy(isOpenPermission = event.isOpen)
            }

            is RecipeWriteDialogEvent.SaveChanged -> {
                stateRecipeWriteDialog = stateRecipeWriteDialog.copy(isOpenSave = event.isOpen)
            }

            is RecipeWriteDialogEvent.UploadCategoryChanged -> {
                stateRecipeWriteDialog =
                    stateRecipeWriteDialog.copy(isOpenUploadCategory = event.isOpen)
            }

            is RecipeWriteDialogEvent.UploadCompleteChanged -> {
                stateRecipeWriteDialog =
                    stateRecipeWriteDialog.copy(isOpenUploadComplete = event.isOpen)
            }
        }
    }

    // stateRecipeWriteBeverages에 대한 event
    fun onRecipeWriteBeveragesEvent(event: RecipeWriteBeveragesEvent) {
        when (event) {
            is RecipeWriteBeveragesEvent.StepFileSelectChanged -> {
                val currentBeverageCheckList =
                    stateRecipeWriteBeverages.beverageCheckList.toMutableList()
                currentBeverageCheckList[event.index] = event.clicked

                stateRecipeWriteBeverages = stateRecipeWriteBeverages.copy(
                    beverageCheckList = currentBeverageCheckList
                )
            }

            is RecipeWriteBeveragesEvent.BtnEnabled -> {
                val isNotChecked = stateRecipeWriteBeverages.beverageCheckList.map {
                    it
                }.all { it == false }

                stateRecipeWriteBeverages = stateRecipeWriteBeverages.copy(
                    btnEnabled = !isNotChecked
                )
            }
        }
    }

    // api
    init {
        getRecipeWriteBeverages()
    }

    suspend fun postRecipeWrite(stepImageParts: List<MultipartBody.Part>): Boolean {
        val ingredients = stateRecipeWriteForm.ingredients.map { ingredient ->
            RecipeWriteIngredient(
                ingredientName = ingredient.ingredientName,
                quantity = ingredient.quantity
            )
        }
        val steps = stateRecipeWriteForm.steps.mapIndexed { index, step ->
            RecipeWriteStep(
                description = step.description,
                stepNum = index + 1
            )
        }
        val categoryId =
            stateRecipeWriteBeverages.beverageCheckList.mapIndexedNotNull { index, isSelected ->
                if (isSelected) index + 1 else null
            }

        // JSON 문자열을 직접 생성
        val content = RecipeWriteContent(
            categoryId = listOf(0) + categoryId,
            ingredientCount = stateRecipeWriteForm.ingredientsNum,
            ingredients = ingredients,
            intro = stateRecipeWriteForm.intro,
            name = stateRecipeWriteForm.title,
            recipeTip = stateRecipeWriteForm.recipeTip,
            stepCount = stateRecipeWriteForm.stepsNum,
            steps = steps,
            time = stateRecipeWriteForm.time
        )
        val gson = Gson()
        val json = gson.toJson(content) // yourDataObject는 요청 본문의 데이터 객체입니다.
        val contentRequestBody = json.toRequestBody("application/json".toMediaTypeOrNull())

        Log.e("recipewrite-result", "${json}")
        Log.e("recipewrite-result 내용", "${stepImageParts}")
        Log.e("recipewrite-result 내용", "${thumbnailPart}")
        Log.e(
            "recipewrite-result 내용",
            "${"Bearer " + dataStore.data.first().accessToken.toString()}"
        )
        var isSuccess = false

        try {
            val result = postRecipeWriteUseCase(
                accessToken = "Bearer " + dataStore.data.first().accessToken.toString(),
                content = contentRequestBody,
                thumbnail = thumbnailPart!!,
                stepImages = stepImageParts,
            )

            result.collect { result ->
                when (result) {
                    is Resource.Success -> {
                        Log.e("recipewrite", "api는 성공 : ${result.message} ${result.code}")
                        if (result.code == 2000) {
                            Log.e("recipewrite", "성공 : ${result.message}")
                            uploadRecipeId = result.data?.result?.recipeId ?: 0
                        }
                        isSuccess = true
                    }

                    is Resource.Error -> {
                        Log.e(
                            "recipewrite",
                            "에러 : ${result} ${result.message} ${result.data} ${result.code}"
                        )
                        isSuccess = false
                    }

                    is Resource.Loading -> {
                        Log.e("recipewrite", "로딩중 : ${result.code}")
                    }
                }
            }
        } catch (e: Exception) {
        }
        return isSuccess
    }

    suspend fun postRecipeWriteTemp(stepImageParts: List<MultipartBody.Part>) {
        val ingredients = stateRecipeWriteForm.ingredients.map { ingredient ->
            RecipeWriteTempIngredient(
                ingredientName = ingredient.ingredientName,
                quantity = ingredient.quantity
            )
        }
        val steps = stateRecipeWriteForm.steps.mapIndexed { index, step ->
            RecipeWriteTempStep(
                description = step.description,
                stepNum = index + 1,
                stepUrl = null,
            )
        }
        // JSON 문자열을 직접 생성
        val content = RecipeWriteTempContent(
            ingredientCount = stateRecipeWriteForm.ingredientsNum,
            ingredients = ingredients,
            intro = stateRecipeWriteForm.intro,
            name = stateRecipeWriteForm.title,
            recipeTip = stateRecipeWriteForm.recipeTip,
            stepCount = stateRecipeWriteForm.stepsNum,
            steps = steps,
            time = stateRecipeWriteForm.time,
            thumbnailUrl = null,
        )
        val gson = Gson()
        val json = gson.toJson(content) // yourDataObject는 요청 본문의 데이터 객체입니다.
        val contentRequestBody = json.toRequestBody("application/json".toMediaTypeOrNull())

        var thumbnail: MultipartBody.Part? = thumbnailPart
        if (thumbnailPart == null || thumbnailPart.toString() == "") {
            thumbnail = null
        }

        var stepImages: List<MultipartBody.Part>? = stepImageParts
        if (stepImages?.all { it == null || it.toString().isEmpty() } == true) {
            stepImages = null
        }

        try {
            val result = postRecipeWriteTempUseCase(
                accessToken = "Bearer " + dataStore.data.first().accessToken.toString(),
                content = contentRequestBody,
                thumbnail = thumbnail,
                stepImages = stepImages,
            )

            result.collect { result ->
                when (result) {
                    is Resource.Success -> {
                        Log.e("recipewritetemp", "api는 성공 : ${result.message} ${result.code}")
                        if (result.code == 2000) {
                            Log.e("recipewritetemp", "성공 : ${result.message}")
                        }
                    }

                    is Resource.Error -> {
                        Log.e(
                            "recipewritetemp",
                            "에러 : ${result} ${result.message} ${result.data} ${result.code}"
                        )
                    }

                    is Resource.Loading -> {
                        Log.e("recipewritetemp", "로딩중 : ${result.code}")
                    }
                }
            }
        } catch (e: Exception) {
        }

    }

    private fun getRecipeWriteBeverages() {
        var accessToken: String = ""
        CoroutineScope(Dispatchers.IO).launch {
            accessToken = "Bearer " + dataStore.data.first().accessToken.toString()
        }

        getRecipeWriteBeveragesUseCase(
            accessToken = accessToken
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    Log.e(
                        "recipewrite-getbeverage",
                        "성공 : ${result.message} ${result.data} ${result.code}"
                    )
                    val beverageList = result.data?.beverageCategoryList ?: emptyList()
                    val filteredBeverageList = beverageList.filter { it.categoryName != "기타" }
                    val beverageCheckList = List(filteredBeverageList.size) { false }

                    stateRecipeWriteBeverages = stateRecipeWriteBeverages.copy(
                        isLoading = false,
                        beverageList = filteredBeverageList,
                        size = filteredBeverageList.size,
                        beverageCheckList = beverageCheckList
                    )
                    Log.e("recipewrite-getbeverage", "${stateRecipeWriteBeverages}")
                }

                is Resource.Error -> {
                    Log.e(
                        "recipewrite-getbeverage",
                        "에러 : ${result} ${result.message} ${result.data} ${result.code}"
                    )
                    stateRecipeWriteBeverages = stateRecipeWriteBeverages.copy(
                        error = result.message ?: "An unexpeted error occured"
                    )
                }

                is Resource.Loading -> {
                    Log.e("recipewrite-getbeverage", "로딩중 : ${result.code}")
                    stateRecipeWriteBeverages = stateRecipeWriteBeverages.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun downloadFile(url: String, targetFile: File) {
            val inputStream = url.byteInputStream()
            val outputStream = FileOutputStream(targetFile)
            val buffer = ByteArray(4096)
            var bytesRead: Int = 0

            // 파일을 다운로드하고 로컬 파일로 저장합니다.
            while (inputStream != null && inputStream.read(buffer).also { bytesRead = it } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }

            outputStream.flush()
            outputStream.close()
            inputStream?.close()
    }

    suspend fun getTempRecipeDetail(tempId: Int) {
        var accessToken = "Bearer " + dataStore.data.first().accessToken.toString()
        Log.e("recipewrite-get-temp", "${tempId} ${accessToken}")

        try {
            val result = getTempRecipeDetailUseCase(accessToken, tempId)

            result.collect { result ->
                when (result) {
                    is Resource.Success -> {

                        Log.e(
                            "recipewrite-get-temp",
                            "성공 : ${result} ${result.message} ${result.data} ${result.code}"
                        )
                        stateRecipeWriteForm = stateRecipeWriteForm.copy(
                            isLoading = false,
                            thumbnail = result.data?.recipeInfo?.thumbnailUrl,
                            title = result.data?.recipeInfo?.recipeName ?: "",
                            titleWordCount = result.data?.recipeInfo?.recipeName?.length ?: 0,
                            time = result.data?.recipeInfo?.time ?: "",
                            timeWordCount = result.data?.recipeInfo?.time?.length ?: 0,
                            intro = result.data?.recipeInfo?.intro ?: "",
                            introWordCount = result.data?.recipeInfo?.intro?.length ?: 0,
                            recipeTip = result?.data?.recipeInfo?.recipeTip ?: "",
                            recipeTipWordCount = result.data?.recipeInfo?.recipeTip?.length ?: 0,
                            btnEnabledSave = false,
                            stepsNum =
                                if(result?.data?.steps?.size == 0) 1
                                else result?.data?.steps?.size ?: 1,
                            steps = result?.data?.steps?.mapIndexed { index, step ->
                                val completeBtnVisible =
                                    step.image == null || step.description == null
                                val isLastStep = index == result.data.steps.size - 1
                                val addBtnVisible = when {
                                    result.data.steps.size == 1 && completeBtnVisible -> false // Case 1
                                    !isLastStep -> false // Case 2
                                    isLastStep && completeBtnVisible -> false // Case 3
                                    else -> true
                                }
                                Step(
                                    stepImage = step.image,
                                    description = step.description ?: "",
                                    stepWordCount = 0,
                                    completeBtnEnabled = false, // Set to an appropriate value
                                    completeBtnVisible = completeBtnVisible,
                                    addBtnVisible = addBtnVisible
                                )
                            } ?: listOf(Step()),
                            ingredients = result?.data?.ingredients?.map { ingredient ->
                                Ingredient(
                                    ingredientName = ingredient.ingredientName,
                                    quantity = ingredient.quantity
                                )
                            } ?: emptyList(),
                            ingredientsNum = result?.data?.ingredients?.size ?: 1,
                        )
                        stateTempRecipeWriteForm = stateTempRecipeWriteForm.copy(
                            isLoading = false,
                            thumbnail = result.data?.recipeInfo?.thumbnailUrl,
                            title = result.data?.recipeInfo?.recipeName ?: "",
                            titleWordCount = result.data?.recipeInfo?.recipeName?.length ?: 0,
                            time = result.data?.recipeInfo?.time ?: "",
                            timeWordCount = result.data?.recipeInfo?.time?.length ?: 0,
                            intro = result.data?.recipeInfo?.intro ?: "",
                            introWordCount = result.data?.recipeInfo?.intro?.length ?: 0,
                            recipeTip = result?.data?.recipeInfo?.recipeTip ?: "",
                            recipeTipWordCount = result.data?.recipeInfo?.recipeTip?.length ?: 0,
                            btnEnabledSave = false,
                            stepsNum =
                            if(result?.data?.steps?.size == 0) 1
                            else result?.data?.steps?.size ?: 1,
                            steps = result?.data?.steps?.mapIndexed { index, step ->
                                val completeBtnVisible =
                                    step.image == null || step.description == null
                                val isLastStep = index == result.data.steps.size - 1
                                val addBtnVisible = when {
                                    result.data.steps.size == 1 && completeBtnVisible -> false // Case 1
                                    !isLastStep -> false // Case 2
                                    isLastStep && completeBtnVisible -> false // Case 3
                                    else -> true
                                }
                                Step(
                                    stepImage = step.image,
                                    description = step.description ?: "",
                                    stepWordCount = 0,
                                    completeBtnEnabled = false, // Set to an appropriate value
                                    completeBtnVisible = completeBtnVisible,
                                    addBtnVisible = addBtnVisible
                                )
                            } ?: listOf(Step()),
                            ingredients = result?.data?.ingredients?.map { ingredient ->
                                Ingredient(
                                    ingredientName = ingredient.ingredientName,
                                    quantity = ingredient.quantity
                                )
                            } ?: emptyList(),
                            ingredientsNum = result?.data?.ingredients?.size ?: 1,
                        )
                        Log.e("recipewrite-get-temp", "${stateRecipeWriteForm}")
                        Log.e("recipewrite-get-temp", "${stateTempRecipeWriteForm}")
                        tempRecipeDetailApiCalled++
                        //Log.e("뭐지", "${tempRecipeDetailApiCalled}")
                    }

                    is Resource.Error -> {
                        Log.e(
                            "recipewrite-get-temp",
                            "에러 : ${result} ${result.message} ${result.data} ${result.code}"
                        )
                        stateRecipeWriteForm = stateRecipeWriteForm.copy(
                            error = result.message ?: "An unexpeted error occured"
                        )
                        stateTempRecipeWriteForm = stateTempRecipeWriteForm.copy(
                            error = result.message ?: "An unexpeted error occured"
                        )
                    }

                    is Resource.Loading -> {
                        Log.e(
                            "recipewrite-get-temp",
                            "로딩중 : ${result} ${result.message} ${result.data} ${result.code}"
                        )
                        stateRecipeWriteForm = stateRecipeWriteForm.copy(isLoading = true)
                        stateTempRecipeWriteForm = stateTempRecipeWriteForm.copy(isLoading = true)
                    }
                }
            }
        } catch (e: Exception) {}

    }

    suspend fun postTempRecipeToTemp( tempId: Int, stepImageParts: List<MultipartBody.Part>, context: Context) {
        var accessToken = "Bearer " + dataStore.data.first().accessToken.toString()
        val ingredients = stateRecipeWriteForm.ingredients.map { ingredient ->
            TempRecipeWriteIngredient(
                ingredientName = ingredient.ingredientName,
                quantity = ingredient.quantity
            )
        }
        var thumbnail: MultipartBody.Part? = null
        var stepImages: List<MultipartBody.Part>? = emptyList()
        // 처음 받아왔을때 상태
        var preStepState = stateRecipeWriteForm.steps.mapIndexed { index, step ->
            TempRecipeWriteStep(
                description = step.description,
                stepNum = index + 1,
                stepUrl = null //step.stepImage?
            )
        }
        // 후에 바뀌었을때 상태
        var nextStepState = stateRecipeWriteForm.steps.mapIndexed { index, step ->
            TempRecipeWriteStep(
                description = step.description,
                stepNum = index + 1,
                stepUrl = null
//                if (step.stepImage?.toString() == "") null
//                else step.stepImage?.toString()
            )
        }

        var content by mutableStateOf(
            TempRecipeWriteContent(
                name = stateRecipeWriteForm.title,
                time = stateRecipeWriteForm.time,
                intro = stateRecipeWriteForm.intro,
                recipeTip = stateRecipeWriteForm.recipeTip,
                ingredientCount = stateRecipeWriteForm.ingredientsNum,
                stepCount = stateRecipeWriteForm.stepsNum,
                ingredients = ingredients,
                thumbnailUrl = null,
                steps = emptyList(),
            )
        )

        // 썸네일 관리
        if (thumbnailPart != null) { //썸네일 새로 추가
            thumbnail = thumbnailPart
            Log.e("recipewrite-post-temp 왜안돼", "첫번째 if문 ${thumbnailPart}")
        }
        else { //썸네일 추가 안한 경우
            thumbnail = null
            // content 안에 thumbnail
            val thumbnailUrl = stateTempRecipeWriteForm.thumbnail.toString()

            /*val tempThumbnailFile = File(context.cacheDir, "temp_thumbnail.jpg")
            downloadFile(thumbnailUrl, tempThumbnailFile)
            // 다운로드한 썸네일 파일을 MultipartBody.Part로 변환
            val thumbnailPart = MultipartBody.Part.createFormData(
                "thumbnail",
                tempThumbnailFile.name,
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), tempThumbnailFile)
            )*/

            content = content.copy(thumbnailUrl = thumbnailUrl)
            Log.e("recipewrite-post-temp 왜안돼", "두번째 if문 ${thumbnailPart}")
            Log.e("recipewrite-post-temp 왜안돼", "content안에 thumbnailUrl : ${thumbnailUrl}")
            Log.e("recipewrite-post-temp 왜안돼", "content안에 thumbnailPart : ${thumbnailPart}")
        }

        // 스텝사진 관리
        if (stepImageParts.isEmpty()) { //스텝 사진 추가 안함
            stepImages = null
            content = content.copy(steps =
                listOf(
                    TempRecipeWriteStep(
                        description = "",
                        stepNum = 1,
                        stepUrl = null

                    )
                )
            ) //스텝 삭제했을때는 content = null 해야함
        } else { //스텝 사진 추가함
            stepImages = stepImageParts
            content = content.copy(steps =
                listOf(
                    TempRecipeWriteStep(
                        description = "",
                        stepNum = 1,
                        stepUrl = null

                    )
                )
            )
        }


        val gson = Gson()
        val json = gson.toJson(content) // yourDataObject는 요청 본문의 데이터 객체입니다.
        val contentRequestBody = json.toRequestBody("application/json".toMediaTypeOrNull())
        Log.e("recipewrite-post-temp", "stateRecipeWriteForm : ${stateRecipeWriteForm}")
        Log.e("recipewrite-post-temp", "stateTempRecipeWriteForm : ${stateTempRecipeWriteForm}")
        Log.e("recipewrite-post-temp", "content안에 thumbnail : ${stateTempRecipeWriteForm.thumbnail.toString()}")
        Log.e("recipewrite-post-temp", "tempId : ${tempId}")
        Log.e("recipewrite-post-temp", "content : ${json}")
        Log.e("recipewrite-post-temp", "thumbnail : ${thumbnail}")
        Log.e("recipewrite-post-temp", "stepImages : ${stepImages}")


        try {
            val result = postAsPatchTempRecipeUseCase(
                accessToken,
                tempId,
                contentRequestBody,
                thumbnail,
                stepImages
            )

            result.collect { result ->
               when(result){
                   is Resource.Success ->{
                       Log.e("recipewrite-post-temp", "성공 : ${result.code}")
                   }
                   is Resource.Error ->{
                       Log.e("recipewrite-post-temp", "에러 : ${result.code}")
                   }
                   is Resource.Loading ->{
                       Log.e("recipewrite-post-temp", "로딩중 : ${result.code}")
                   }
               }
            }

        } catch (e: Exception) {}
    }

    suspend fun postSaveTempRecipe() {

    }

}

