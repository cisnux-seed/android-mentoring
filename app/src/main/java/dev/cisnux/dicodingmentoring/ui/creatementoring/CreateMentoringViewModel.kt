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
import dev.cisnux.dicodingmentoring.utils.withDateFormat
import dev.cisnux.dicodingmentoring.utils.withTimeFormat
import java.util.Calendar
import java.util.Date
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
    private var mentoringDateMillis = 0L
    private var mentoringTimeMillis = 0L
    private val _mentoringDate = mutableStateOf("")
    val mentoringDate: State<String> get() = _mentoringDate
    private val _mentoringTime = mutableStateOf("")
    val mentoringTime get() = _mentoringTime
    fun onMentoringDateChanged(selectedDate: Long?) {
        selectedDate?.let { dateMillis ->
            val todayCal = Calendar.getInstance()
            val today = todayCal.get(Calendar.DAY_OF_MONTH)
            val selectedCal = Calendar.getInstance()
            selectedCal.time = Date(dateMillis)
            val currentDate = selectedCal.get(Calendar.DAY_OF_MONTH)
            if (currentDate >= today) {
                mentoringDateMillis = dateMillis
                _mentoringDate.value = selectedDate.withDateFormat()
            }
        }
    }

    fun onMentoringTimeChanged(selectedTime: Long?) {
        selectedTime?.let { timeMillis ->
            if (timeMillis >= System.currentTimeMillis()) {
                mentoringTimeMillis = timeMillis
                _mentoringTime.value = timeMillis.withTimeFormat()
            }
        }
    }

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