package dev.cisnux.dicodingmentoring.ui.login

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInClient
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
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val googleClient: GoogleSignInClient
) : ViewModel() {
    private val _loginState = MutableStateFlow<UiState<AuthenticatedUser>>(UiState.Loading)
    val loginState: StateFlow<UiState<AuthenticatedUser>> get() = _loginState
    val googleSignInClient get() = googleClient

    private val _email = mutableStateOf("")
    val email: State<String> get() = _email
    val isValidEmail: State<Boolean> = derivedStateOf {
        if (_email.value.isNotEmpty())
            _email.value.isEmail()
        else false
    }

    private val _password = mutableStateOf("")
    val password: State<String> get() = _password
    val isValidPassword: State<Boolean> = derivedStateOf {
        if (_password.value.isNotEmpty())
            _password.value.isPasswordSecure()
        else false
    }

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> get() = _isLoading

    private val _isPasswordVisible = mutableStateOf(false)
    val isPasswordVisible: State<Boolean> get() = _isPasswordVisible

    fun onPasswordVisible(visible: Boolean) {
        _isPasswordVisible.value = !visible
    }

    fun login() = viewModelScope.launch {
        _isLoading.value = true
        val authUser = AuthUser(email = _email.value, password = _password.value)
        val result = repository.login(authUser)
        result.fold(
            {
                _isLoading.value = false
                _loginState.value = UiState.Error(it)
            }, {
                _isLoading.value = false
                _loginState.value = UiState.Success(it)
            }
        )
    }

    fun googleSignIn(token: String?) = viewModelScope.launch {
        _isLoading.value = false
        val result = repository.signInWithGoogle(token)
        result.fold(
            {
                _isLoading.value = false
                _loginState.value = UiState.Error(it)
            }, {
                _isLoading.value = false
                _loginState.value = UiState.Success(it)
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