package com.zipdabang.zipdabang_android.module.my.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.my.ui.state.recipewrite.RecipeWriteDialogEvent
import com.zipdabang.zipdabang_android.module.my.ui.state.recipewrite.RecipeWriteDialogState
import com.zipdabang.zipdabang_android.module.my.ui.state.recipewrite.RecipeWriteFormEvent
import com.zipdabang.zipdabang_android.module.my.ui.state.recipewrite.RecipeWriteFormState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecipeWriteViewModel @Inject constructor(
    private val dataStore: DataStore<Token>,
) : ViewModel() {

    var stateRecipeWriteForm by mutableStateOf(RecipeWriteFormState())
    var stateRecipeWriteDialog by mutableStateOf(RecipeWriteDialogState())

    fun onRecipeWriteFormEvent(event : RecipeWriteFormEvent){
        when(event){
            is RecipeWriteFormEvent.titleChanged->{
                stateRecipeWriteForm = stateRecipeWriteForm.copy(
                    title = event.title,
                    titleWordCount = event.title.length
                )
            }
            is RecipeWriteFormEvent.timeChanged->{
                stateRecipeWriteForm = stateRecipeWriteForm.copy(
                    time = event.time,
                    timeWordCount = event.time.length
                )
            }
            is RecipeWriteFormEvent.introChanged->{
                stateRecipeWriteForm = stateRecipeWriteForm.copy(
                    intro = event.intro,
                    introWordCount = event.intro.length
                )
            }
            is RecipeWriteFormEvent.recipeTipChanged->{
                stateRecipeWriteForm = stateRecipeWriteForm.copy(
                    recipeTip = event.recipeTip,
                    recipeTipWordCount = event.recipeTip.length
                )
            }
            is RecipeWriteFormEvent.BtnEnabled->{

            }
        }
    }

    fun onRecipeWriteDialogEvent(event : RecipeWriteDialogEvent){
        when(event){
            is RecipeWriteDialogEvent.RecipeDeleteChanged->{

            }
            is RecipeWriteDialogEvent.FileSelectChanged->{

            }
            is RecipeWriteDialogEvent.PermissionChanged->{

            }
            is RecipeWriteDialogEvent.SaveChanged->{

            }
            is RecipeWriteDialogEvent.UploadCategoryChanged->{

            }
            is RecipeWriteDialogEvent.UploadCompleteChanged->{

            }
        }
    }





}