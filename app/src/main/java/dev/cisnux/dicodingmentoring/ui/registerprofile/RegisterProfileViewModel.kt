package dev.cisnux.dicodingmentoring.ui.registerprofile

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.cisnux.dicodingmentoring.domain.models.AddUserProfile
import dev.cisnux.dicodingmentoring.domain.repositories.AuthRepository
import dev.cisnux.dicodingmentoring.domain.repositories.UserProfileRepository
import dev.cisnux.dicodingmentoring.utils.UiState
import dev.cisnux.dicodingmentoring.utils.isValidAbout
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userProfileRepo: UserProfileRepository
) : ViewModel() {
    private val _fullName = mutableStateOf("")
    val fullName: State<String> get() = _fullName
    private val _username = mutableStateOf("")
    val username: State<String> get() = _username
    private val _job = mutableStateOf("")
    val job: State<String> get() = _job
    private val _experienceLevel = mutableStateOf("")
    val experienceLevel: State<String> get() = _experienceLevel
    private var _about = mutableStateOf("")
    val about: State<String> get() = _about

    private val _createProfileState = MutableStateFlow<UiState<Nothing?>>(UiState.Initialize)
    val createProfileState: StateFlow<UiState<Nothing?>> get() = _createProfileState

    private val _pictureFromGallery = mutableStateOf<Uri?>(null)
    val pictureFromGallery: State<Uri?> get() = _pictureFromGallery

    private val checkedInterests = mutableListOf<String>()
    val isCheckedEmpty get() = checkedInterests.isEmpty()


    init {
        Log.d(
            RegisterProfileViewModel::class.simpleName,
            authRepository.currentUser()?.toString() ?: "no user"
        )
    }

    fun addInterest(interest: String, checked: Boolean) {
        if (checked) checkedInterests.add(interest)
        else checkedInterests.remove(
            interest
        )
        Log.d(RegisterProfileViewModel::class.simpleName, checkedInterests.toString())
    }

    fun setPhotoFromGallery(uri: Uri) {
        _pictureFromGallery.value = uri
    }

    fun onFullNameQueryChanged(fullName: String) {
        _fullName.value = fullName
    }

    fun onUsernameQueryChanged(username: String) {
        _username.value = username
    }

    fun onJobQueryChanged(job: String) {
        _job.value = job
    }

    fun onAboutQueryChanged(about: String) {
        if (about.isValidAbout(80))
            _about.value = about
    }

    fun onExperienceLevelOption(experienceLevel: String) {
        _experienceLevel.value = experienceLevel
    }

    fun saveAuthSession(id: String, session: Boolean) = viewModelScope.launch {
        authRepository.saveAuthSession(id, session)
    }

    fun onCreateProfile(id: String) = viewModelScope.launch {
        _createProfileState.value = UiState.Loading
        val email = authRepository.currentUser()?.email

        email?.let { userEmail ->
            val addUserProfile = AddUserProfile(
                id = id,
                fullName = fullName.value,
                username = username.value,
                email = userEmail,
                job = job.value,
                experienceLevel = experienceLevel.value,
                interests = checkedInterests,
                photoProfileUri = pictureFromGallery.value,
                about = about.value
            )
            userProfileRepo.postUserProfile(addUserProfile).fold(
                {
                    _createProfileState.value = UiState.Error(it)
                },
                {
                    _createProfileState.value = UiState.Success(it)
                }
            )
        }
    }
}