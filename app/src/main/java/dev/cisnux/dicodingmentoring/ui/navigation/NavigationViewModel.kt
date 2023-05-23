package dev.cisnux.dicodingmentoring.ui.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.cisnux.dicodingmentoring.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _authSession = MutableStateFlow<Boolean?>(null)
    val authSession: StateFlow<Boolean?> get() = _authSession

    init {
        viewModelScope.launch {
            _authSession.value = authRepository.getAuthSession(
                authRepository.currentUser()?.uid ?: ""
            ).first()
        }
    }

    fun cancelPrevSession() = authRepository.logout()
//    val isAlreadyLoggedIn get() = authRepository.isAlreadyLoggedIn(viewModelScope)
//
//    private val _isAlreadyHaveProfile = MutableStateFlow(false)
//    val isAlreadyHaveProfile: StateFlow<Boolean> = _isAlreadyHaveProfile
//
//    init {
//        getUserProfileSession()
//    }
//
//    private fun getUserProfileSession() = viewModelScope.launch {
//        val id = authRepository.currentUser(viewModelScope).last()?.uid
//        _isAlreadyHaveProfile.value =
//            id?.let { userProfileRepository.isUserProfileExist(it).last() } ?: false
//    }
}