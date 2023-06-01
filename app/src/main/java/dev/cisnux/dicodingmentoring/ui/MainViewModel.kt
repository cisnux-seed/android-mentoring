package dev.cisnux.dicodingmentoring.ui

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.cisnux.dicodingmentoring.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _authSession = MutableStateFlow<Boolean?>(null)
    val authSession get() = _authSession.asStateFlow()

    private val _shouldBottomBarOpen = mutableStateOf(false)
    val shouldBottomBarOpen: State<Boolean> get() = _shouldBottomBarOpen
    private val _isRefreshPrevContent = mutableStateOf(false)
    val isRefreshPrevContent: State<Boolean> get() = _isRefreshPrevContent

    private var currentUserId = authRepository.currentUser().map {
        it.uid
    }

    fun updateBottomState(shouldBottomBarOpen: Boolean) {
        Log.d("MainViewModel", shouldBottomBarOpen.toString())
        _shouldBottomBarOpen.value = shouldBottomBarOpen
    }

    fun refreshPrevContent(refreshPrevContent: Boolean) {
        _isRefreshPrevContent.value = refreshPrevContent
    }

    init {
        viewModelScope.launch {
            _authSession.value = authRepository.getAuthSession(currentUserId.first() ?: "").first()
            // close bottom bar when there is no auth session
            if (_authSession.value != true) {
                authRepository.logout()
            } else {
                _shouldBottomBarOpen.value = true
            }
        }
    }

    fun logout() = viewModelScope.launch {
        _authSession.value = false
        currentUserId.first()?.let { id ->
            authRepository.logout(id)
        }
    }
}