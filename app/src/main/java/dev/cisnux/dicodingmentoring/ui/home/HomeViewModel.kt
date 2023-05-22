package dev.cisnux.dicodingmentoring.ui.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.cisnux.dicodingmentoring.domain.models.AuthenticatedUser
import dev.cisnux.dicodingmentoring.domain.repositories.AuthRepository
import dev.cisnux.dicodingmentoring.domain.repositories.UserProfileRepository
import dev.cisnux.dicodingmentoring.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userProfileRepository: UserProfileRepository,
) : ViewModel() {
    val currentUser: StateFlow<AuthenticatedUser?> get() = _currentUser
    private val _currentUser = MutableStateFlow<AuthenticatedUser?>(null)
    private val _isLogin = mutableStateOf(false)
    val isLogin: State<Boolean> get() = _isLogin
    private val _isProfileExist = mutableStateOf(false)
    val isProfileExist: State<Boolean> get() = _isProfileExist
    private val _userProfileState = MutableStateFlow<UiState<Boolean>>(UiState.Loading)
    val userProfileState: StateFlow<UiState<Boolean>> get() = _userProfileState

    init {
        getCurrentUser()
    }

    private fun getCurrentUser() = viewModelScope.launch {
        authRepository.currentUser().collect { user ->
            _currentUser.value = user
            _isLogin.value = user != null
            user?.let {
                if (_isLogin.value) {
                    userProfileRepository.isUserProfileExist(user.uid).collect {
                        _userProfileState.value = it
                    }
                }
            }
        }
    }
}