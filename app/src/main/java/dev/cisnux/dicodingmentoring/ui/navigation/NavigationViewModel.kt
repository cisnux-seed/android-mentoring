package dev.cisnux.dicodingmentoring.ui.navigation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.cisnux.dicodingmentoring.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _authSession = MutableStateFlow<Boolean?>(null)
    val authSession get() = _authSession.asStateFlow()

    init {
        viewModelScope.launch {
            _authSession.value = authRepository.getAuthSession(
                authRepository.currentUser()?.uid ?: ""
            ).first()
            if(_authSession.value != true){
                authRepository.logout()
                Log.d("AppNavGraph", "call again")
            }
        }
    }
}