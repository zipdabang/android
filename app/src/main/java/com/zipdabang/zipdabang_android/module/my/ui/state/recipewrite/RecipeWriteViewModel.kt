package com.zipdabang.zipdabang_android.module.my.ui.state.recipewrite

import android.media.session.MediaSession.Token
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

//@HiltViewModel
class RecipeWriteViewModel @Inject constructor(
    private val dataStore : DataStore<Token>
){
    //var stateRecipeWrite by mutableStateOf(RecipeWriteFormState())

}