package dev.cisnux.dicodingmentoring.ui.myprofile

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.cisnux.dicodingmentoring.domain.models.GetUserProfile
import dev.cisnux.dicodingmentoring.domain.repositories.AuthRepository
import dev.cisnux.dicodingmentoring.domain.repositories.UserProfileRepository
import dev.cisnux.dicodingmentoring.utils.UiState
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userProfileRepository: UserProfileRepository
) : ViewModel() {
    private val _myProfileState = mutableStateOf<UiState<GetUserProfile>>(UiState.Initialize)
    val myProfileState: State<UiState<GetUserProfile>> get() = _myProfileState

    init {
        Log.d(MyProfileViewModel::class.simpleName, authRepository.currentUser()?.uid ?: "no id")
    }

    fun getUserProfile() = viewModelScope.launch {
        Log.d(MyProfileViewModel::class.simpleName, "success")
        _myProfileState.value = UiState.Loading
        Log.d(MyProfileViewModel::class.simpleName, authRepository.currentUser()?.uid ?: "no id")
        val result = authRepository.currentUser()?.uid?.let { id ->
            userProfileRepository.getUserProfileById(id)
        }
        result?.fold({
            _myProfileState.value = UiState.Error(it)
        }, {
            Log.d(MyProfileViewModel::class.simpleName, "success")
            _myProfileState.value = UiState.Success(it)
        })
    }
}