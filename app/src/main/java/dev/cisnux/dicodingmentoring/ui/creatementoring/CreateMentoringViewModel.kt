package dev.cisnux.dicodingmentoring.ui.creatementoring

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.cisnux.dicodingmentoring.DicodingMentoringApplication
import dev.cisnux.dicodingmentoring.R
import javax.inject.Inject

@HiltViewModel
class CreateMentoringViewModel @Inject constructor(
    @ApplicationContext appContext: Context,
    savedStateHandle: SavedStateHandle,
) : AndroidViewModel(appContext as Application) {
    private val id = checkNotNull(savedStateHandle["id"]) as String
    private val context = getApplication<DicodingMentoringApplication>()

    private val _title = mutableStateOf("")
    val title: State<String> get() = _title
    private val _description = mutableStateOf("")
    val description: State<String> get() = _description
    private val _mentoringType = mutableStateOf(context.getString(R.string.chat))
    val mentoringType: State<String> get() = _mentoringType
    val currentTimeMillis = System.currentTimeMillis()
    private val _mentoringDate = mutableStateOf("")
    val mentoringDate: State<String> get() = _mentoringDate

    fun onTitleChanged(title: String) {
        _title.value = title
    }

    fun onDescriptionChanged(description: String) {
        _description.value = description
    }

    fun onMentoringTypeSelected(mentoringType: String) {
        _mentoringType.value = mentoringType
        Log.d(CreateMentoringViewModel::class.simpleName, _mentoringType.value)
    }
}