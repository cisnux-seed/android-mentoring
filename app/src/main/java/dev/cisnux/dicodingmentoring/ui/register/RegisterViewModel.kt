package dev.cisnux.dicodingmentoring.ui.register

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.cisnux.dicodingmentoring.domain.models.AuthUser
import dev.cisnux.dicodingmentoring.domain.models.AuthenticatedUser
import dev.cisnux.dicodingmentoring.domain.repositories.AuthRepository
import dev.cisnux.dicodingmentoring.utils.UiState
import dev.cisnux.dicodingmentoring.utils.isEmail
import dev.cisnux.dicodingmentoring.utils.isPasswordSecure
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    private val _registerState = MutableStateFlow<UiState<AuthenticatedUser>>(UiState.Loading)
    val registerState: StateFlow<UiState<AuthenticatedUser>> get() = _registerState

    private val _email = mutableStateOf("")
    val email: State<String> get() = _email
    val isValidEmail: State<Boolean> = derivedStateOf {
        if (_email.value.isNotEmpty())
            _email.value.isEmail()
        else true
    }

    private val _password = mutableStateOf("")
    val password: State<String> get() = _password
    val isValidPassword: State<Boolean> = derivedStateOf {
        if (_password.value.isNotEmpty())
            _password.value.isPasswordSecure()
        else true
    }

    private val _isPasswordVisible = mutableStateOf(false)
    val isPasswordVisible: State<Boolean> get() = _isPasswordVisible

    fun onPasswordVisible(visible: Boolean) {
        _isPasswordVisible.value = !visible
    }

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> get() = _isLoading

    fun register() = viewModelScope.launch {
        _isLoading.value = true
        val authUser = AuthUser(email = _email.value, password = _password.value)
        val result = repository.register(authUser)
        result.fold(
            {
                _isLoading.value = false
                _registerState.value = UiState.Error(it)
            }, {
                _isLoading.value = false
                _registerState.value = UiState.Success(it)
            }
        )
    }

    fun onEmailQueryChanged(email: String) {
        _email.value = email
    }

    fun onPasswordQueryChanged(password: String) {
        _password.value = password
    }
}