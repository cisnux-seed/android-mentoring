package dev.cisnux.dicodingmentoring.ui.matchmentoring

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.cisnux.dicodingmentoring.domain.repositories.AuthRepository
import javax.inject.Inject

@HiltViewModel
class MatchMakingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val authRepository: AuthRepository,
) : ViewModel() {
    val currentUser get() = authRepository.currentUser()

    private val checkedInterests = mutableSetOf<String>()
    val isCheckedEmpty get() = checkedInterests.isEmpty()

    private val _experienceLevel = mutableStateOf("")
    val experienceLevel: State<String> get() = _experienceLevel

    private val _problem = mutableStateOf("")
    val problem: State<String> get() = _problem

    fun addLearningPath(interest: String, checked: Boolean) {
        if (checked) checkedInterests.add(interest)
        else checkedInterests.remove(
            interest
        )
    }

    fun onProblemChanged(problem: String) {
        _problem.value = problem
    }

    fun onExperienceLevelOption(experienceLevel: String) {
        _experienceLevel.value = experienceLevel
    }
}