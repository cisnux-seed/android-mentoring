package dev.cisnux.dicodingmentoring.ui.myprofile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.cisnux.dicodingmentoring.domain.models.GetUserProfile
import dev.cisnux.dicodingmentoring.domain.repositories.AuthRepository
import dev.cisnux.dicodingmentoring.domain.repositories.UserProfileRepository
import dev.cisnux.dicodingmentoring.utils.UiState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userProfileRepository: UserProfileRepository
) : ViewModel() {
    private val _myProfileState = mutableStateOf<UiState<GetUserProfile>>(UiState.Initialize)
    val myProfileState: State<UiState<GetUserProfile>> get() = _myProfileState
    private val _isMentorValid = mutableStateOf(true)
    val isMentorValid: State<Boolean> get() = _isMentorValid
    private val _currentUserId = mutableStateOf("")
    val currentUserId: State<String> get() = _currentUserId

    init {
        viewModelScope.launch {
            authRepository.currentUser().collectLatest {
                _currentUserId.value = it.uid
                getUserProfile(it.uid)
            }
        }
    }

    fun getUserProfile(id: String) = viewModelScope.launch {
        if (id.isNotBlank()) {
            _myProfileState.value = UiState.Loading
            val result = userProfileRepository.getUserProfileById(id)
            result.fold({ exception ->
                _myProfileState.value = UiState.Error(exception)
            }, { getUserProfile ->
                _myProfileState.value = UiState.Success(getUserProfile)
                _isMentorValid.value = getUserProfile.isMentorValid
            })
        }
    }
}