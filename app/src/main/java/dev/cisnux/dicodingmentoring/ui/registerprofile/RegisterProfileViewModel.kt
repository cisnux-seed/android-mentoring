package dev.cisnux.dicodingmentoring.ui.registerprofile

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.state.ToggleableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.cisnux.dicodingmentoring.domain.models.AddUserProfile
import dev.cisnux.dicodingmentoring.domain.repositories.AuthRepository
import dev.cisnux.dicodingmentoring.domain.repositories.UserProfileRepository
import dev.cisnux.dicodingmentoring.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userProfileRepo: UserProfileRepository
) :
    ViewModel() {
    private val _fullName = mutableStateOf("")
    val fullName: State<String> get() = _fullName
    private val _username = mutableStateOf("")
    val username: State<String> get() = _username
    private val _job = mutableStateOf("")
    val job: State<String> get() = _job
    private val _experienceLevel = mutableStateOf("")
    val experienceLevel: State<String> get() = _experienceLevel
    private val _motto = mutableStateOf("")
    val motto: State<String> get() = _motto
    private val _isExpanded = mutableStateOf(false)
    val isExpanded: State<Boolean> get() = _isExpanded

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> get() = _isLoading

    private val _checked1State = mutableStateOf(true)
    val checked1State: State<Boolean> get() = _checked1State
    private val _checked2State = mutableStateOf(true)
    val checked2State: State<Boolean> get() = _checked2State
    private val _checked3State = mutableStateOf(true)
    val checked3State: State<Boolean> get() = _checked3State
    private val _checked4State = mutableStateOf(true)
    val checked4State: State<Boolean> get() = _checked4State
    private val _checked5State = mutableStateOf(true)
    val checked5State: State<Boolean> get() = _checked5State
    val checkedParentState = derivedStateOf {
        if (_checked1State.value and _checked2State.value and _checked3State.value
            and _checked4State.value and _checked5State.value
        ) ToggleableState.On
        else if (!_checked1State.value and !_checked2State.value and !_checked3State.value
            and !_checked4State.value and !_checked5State.value
        ) ToggleableState.Off
        else ToggleableState.Indeterminate
    }

    private val _createProfileState = MutableStateFlow<UiState<Nothing>>(UiState.Loading)
    val createProfileState: StateFlow<UiState<Nothing>> get() = _createProfileState

    private val _pictureFromGallery = mutableStateOf<Uri?>(null)
    val pictureFromGallery: State<Uri?> get() = _pictureFromGallery

    private val checkedInterests =
        mutableListOf("Android", "iOS", "Front-End", "Back-End", "Cloud Computing")
    val interests: List<String> = listOf(*checkedInterests.toTypedArray())
    val isCheckedEmpty = checkedInterests.isEmpty()

    fun onChecked1State(checked: Boolean) {
        _checked1State.value = checked
        if (checked)
            checkedInterests.add(interests.component1())
        else checkedInterests.remove(interests.component1())
    }

    fun onChecked2State(checked: Boolean) {
        _checked2State.value = checked
        if (checked)
            checkedInterests.add(interests.component2())
        else checkedInterests.remove(interests.component2())
    }

    fun onChecked3State(checked: Boolean) {
        _checked3State.value = checked
        if (checked)
            checkedInterests.add(interests.component3())
        else checkedInterests.remove(interests.component3())
    }

    fun onChecked4State(checked: Boolean) {
        _checked4State.value = checked
        if (checked)
            checkedInterests.add(interests.component4())
        else checkedInterests.remove(interests.component4())
    }

    fun onChecked5State(checked: Boolean) {
        _checked5State.value = checked
        if (checked)
            checkedInterests.add(interests.component5())
        else checkedInterests.remove(interests.component5())
        Log.d(RegisterProfileViewModel::class.simpleName, checkedInterests.toString())
    }

    fun setPhotoFromGallery(uri: Uri) {
        _pictureFromGallery.value = uri
    }

    fun onFullNameQueryChanged(fullName: String) {
        _fullName.value = fullName
    }

    fun onExpandedChanged(isExpanded: Boolean) {
        Log.d(RegisterProfileViewModel::class.simpleName, isExpanded.toString())
        _isExpanded.value = isExpanded
    }

    fun onUsernameQueryChanged(username: String) {
        _username.value = username
    }

    fun onJobQueryChanged(job: String) {
        _job.value = job
    }

    fun onMottoQueryChanged(motto: String) {
        _motto.value = motto
    }

    fun onExperienceLevelOption(experienceLevel: String) {
        _experienceLevel.value = experienceLevel
    }

    fun onCreateProfile(id: String) = viewModelScope.launch {
        _isLoading.value = true
        val email = authRepository.currentUser().last()?.email

        email?.let {
            val addUserProfile = AddUserProfile(
                id = id,
                fullName = fullName.value,
                username = username.value,
                email = it,
                job = job.value,
                experienceLevel = experienceLevel.value,
                interests = emptyList(),
                photoProfileUri = pictureFromGallery.value,
                motto = motto.value
            )
            userProfileRepo.postUserProfile(addUserProfile).collect { uiState ->
                _isLoading.value = uiState != UiState.Loading
                _createProfileState.value = uiState
            }
        }
    }
}