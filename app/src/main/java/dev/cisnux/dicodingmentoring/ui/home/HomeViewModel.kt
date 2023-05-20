package dev.cisnux.dicodingmentoring.ui.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.cisnux.dicodingmentoring.domain.repositories.AuthRepository
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    private val _isLogin = mutableStateOf(false)
    val isLogin: State<Boolean> get() = _isLogin

    init {
        getCurrentUser()
    }

    private fun getCurrentUser() = viewModelScope.launch {
        repository.currentUser().collect { user ->
            _isLogin.value = user != null
        }
    }
}