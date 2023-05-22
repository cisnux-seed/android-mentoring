package dev.cisnux.dicodingmentoring.ui.registerprofile

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import dev.cisnux.dicodingmentoring.R
import dev.cisnux.dicodingmentoring.ui.components.rememberGalleryLauncher
import dev.cisnux.dicodingmentoring.ui.theme.DicodingMentoringTheme
import dev.cisnux.dicodingmentoring.utils.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun RegisterProfileScreen(
    id: String,
    onNavigateToHome: () -> Unit,
    takePictureFromGallery: (launcher: ManagedActivityResultLauncher<Intent, ActivityResult>) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RegisterProfileViewModel = hiltViewModel()
) {
    val fullName by viewModel.fullName
    val username by viewModel.username
    val job by viewModel.job
    val experienceLevel by viewModel.experienceLevel
    val motto by viewModel.motto
    val isExpanded by viewModel.isExpanded
    val pictureFromGallery by viewModel.pictureFromGallery
    val isLoading by viewModel.isLoading
    val isCheckedEmpty = viewModel.isCheckedEmpty
    val checked1State by viewModel.checked1State
    val checked2State by viewModel.checked2State
    val checked3State by viewModel.checked3State
    val checked4State by viewModel.checked4State
    val checked5State by viewModel.checked5State
    val checkedParentState by viewModel.checkedParentState

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val checkBoxItems = listOf(
        CheckBoxItem(viewModel.interests.component1(), checked1State, viewModel::onChecked1State),
        CheckBoxItem(viewModel.interests.component2(), checked2State, viewModel::onChecked2State),
        CheckBoxItem(viewModel.interests.component3(), checked3State, viewModel::onChecked3State),
        CheckBoxItem(viewModel.interests.component4(), checked4State, viewModel::onChecked4State),
        CheckBoxItem(viewModel.interests.component5(), checked5State, viewModel::onChecked5State),
    )
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val launcher = rememberGalleryLauncher(onSuccess = {
        viewModel.setPhotoFromGallery(uri = it)
    })

    val createProfileState by viewModel.createProfileState.collectAsStateWithLifecycle()

    when (createProfileState) {
        is UiState.Error -> {
            (createProfileState as UiState.Error).error?.message?.let { message ->
                LaunchedEffect(snackbarHostState) {
                    snackbarHostState.showSnackbar(message)
                }
            }
        }

        is UiState.Success -> {
            onNavigateToHome()
        }

        else -> {
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        RegisterProfileBody(
            fullName = fullName,
            username = username,
            job = job,
            onCreateProfile = {
                viewModel.onCreateProfile(id)
            },
            onProfilePicture = { takePictureFromGallery(launcher) },
            isExpanded = isExpanded,
            onExpandedChanged = viewModel::onExpandedChanged,
            onExperienceLevelOption = viewModel::onExperienceLevelOption,
            experienceLevelOption = experienceLevel,
            motto = motto,
            onFullNameQueryChanged = viewModel::onFullNameQueryChanged,
            onUsernameQueryChanged = viewModel::onUsernameQueryChanged,
            onJobQueryChanged = viewModel::onJobQueryChanged,
            onMottoQueryChanged = viewModel::onMottoQueryChanged,
            snackbarHostState = snackbarHostState,
            scope = scope,
            context = context,
            scrollState = scrollState,
            keyboardController = keyboardController,
            modifier = Modifier.padding(innerPadding),
            imageUri = pictureFromGallery,
            isLoading = isLoading,
            isCheckedEmpty = isCheckedEmpty,
            checkedParentState = checkedParentState,
            checkBoxItems = checkBoxItems
        )
    }

}

@OptIn(ExperimentalComposeUiApi::class)
@Preview(
    showBackground = true, showSystemUi = false,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun RegisterProfileBodyPreview() {
    DicodingMentoringTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            RegisterProfileBody(
                fullName = "",
                username = "",
                job = "",
                experienceLevelOption = "",
                motto = "",
                onFullNameQueryChanged = {},
                onUsernameQueryChanged = {},
                onJobQueryChanged = {},
                onMottoQueryChanged = {},
                keyboardController = null,
                onCreateProfile = {},
                context = LocalContext.current,
                snackbarHostState = SnackbarHostState(),
                scope = rememberCoroutineScope(),
                isExpanded = false,
                onProfilePicture = {},
                onExpandedChanged = {},
                onExperienceLevelOption = {},
                scrollState = rememberScrollState(),
                isLoading = true,
                isCheckedEmpty = false,
                checkedParentState = ToggleableState.On,
                checkBoxItems = listOf(
                    CheckBoxItem("Android", true) {},
                    CheckBoxItem("Android", true) {},
                    CheckBoxItem("Android", true) {},
                    CheckBoxItem("Android", true) {},
                    CheckBoxItem("Android", true) {},
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun RegisterProfileBody(
    fullName: String,
    username: String,
    job: String,
    onCreateProfile: () -> Unit,
    onProfilePicture: () -> Unit,
    isExpanded: Boolean,
    onExpandedChanged: (Boolean) -> Unit,
    onExperienceLevelOption: (String) -> Unit,
    experienceLevelOption: String,
    motto: String,
    isLoading: Boolean,
    checkedParentState: ToggleableState,
    onFullNameQueryChanged: (String) -> Unit,
    onUsernameQueryChanged: (String) -> Unit,
    onJobQueryChanged: (String) -> Unit,
    onMottoQueryChanged: (String) -> Unit,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
    context: Context,
    scrollState: ScrollState,
    keyboardController: SoftwareKeyboardController?,
    isCheckedEmpty: Boolean,
    checkBoxItems: List<CheckBoxItem>,
    modifier: Modifier = Modifier,
    imageUri: Uri? = null,
) {
    val options = listOf("Beginner", "Intermediate", "Expert")

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(state = scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        imageUri?.let {
            AsyncImage(
                model = imageUri,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onProfilePicture)
            )
        } ?: Image(
            painter = painterResource(id = R.drawable.profile_picture_placeholder),
            contentDescription = null,
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .clickable(onClick = onProfilePicture),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Set up your profile ✍🏼",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Create an account so you can mentoring\nwith professional mentors",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = fullName,
            onValueChange = onFullNameQueryChanged,
            modifier = Modifier
                .fillMaxWidth(),
            maxLines = 1,
            leadingIcon = {
                Icon(
                    tint = MaterialTheme.colorScheme.primary,
                    imageVector = Icons.Filled.Person,
                    contentDescription = null
                )
            },
            supportingText = {
                if (fullName.isNotEmpty() && fullName.isBlank())
                    Text(text = "Please enter your full name")
            },
            placeholder = {
                Text(text = "Enter your full name")
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                disabledBorderColor = MaterialTheme.colorScheme.primary,
            ),
            isError = fullName.isNotEmpty() && fullName.isBlank()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = username,
            onValueChange = onUsernameQueryChanged,
            modifier = Modifier
                .fillMaxWidth(),
            maxLines = 1,
            leadingIcon = {
                Icon(
                    tint = MaterialTheme.colorScheme.primary,
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = null
                )
            },
            supportingText = {
                if (username.isNotEmpty() && username.isBlank())
                    Text(text = "Please enter your username")
            },
            placeholder = {
                Text(text = "Enter your username")
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                disabledBorderColor = MaterialTheme.colorScheme.primary,
            ),
            isError = username.isNotEmpty() && username.isBlank()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = job,
            onValueChange = onJobQueryChanged,
            modifier = Modifier
                .fillMaxWidth(),
            maxLines = 1,
            leadingIcon = {
                Icon(
                    tint = MaterialTheme.colorScheme.primary,
                    painter = painterResource(id = R.drawable.ic_outline_work_24),
                    contentDescription = null
                )
            },
            supportingText = {
                if (job.isNotEmpty() && job.isBlank())
                    Text(text = "Please enter your job")
            },
            placeholder = {
                Text(text = "Enter your job")
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                disabledBorderColor = MaterialTheme.colorScheme.primary,
            ),
            isError = job.isNotEmpty() && job.isBlank()
        )
        Spacer(modifier = Modifier.height(8.dp))
        ExposedDropdownMenuBox(expanded = isExpanded, onExpandedChange = { isExpanded ->
            onExpandedChanged(isExpanded)
        }) {
            OutlinedTextField(
                value = experienceLevelOption,
                onValueChange = {},
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                maxLines = 1,
                readOnly = true,
                leadingIcon = {
                    Icon(
                        tint = MaterialTheme.colorScheme.primary,
                        painter = painterResource(id = R.drawable.ic_xp_24),
                        contentDescription = null,
                        modifier = Modifier.size(28.dp)
                    )
                },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
                supportingText = {
                    if (experienceLevelOption.isNotEmpty() && experienceLevelOption.isBlank())
                        Text(text = "Please enter your experience level")
                },
                placeholder = {
                    Text(text = "Enter your experience level")
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                    disabledBorderColor = MaterialTheme.colorScheme.primary,
                ),
                isError = experienceLevelOption.isNotEmpty() && experienceLevelOption.isBlank()
            )
            ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = {
                onExpandedChanged(false)
            }) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            onExperienceLevelOption(selectionOption)
                            onExpandedChanged(false)
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = motto,
            onValueChange = onMottoQueryChanged,
            modifier = Modifier
                .fillMaxWidth(),
            maxLines = 1,
            leadingIcon = {
                Icon(
                    tint = MaterialTheme.colorScheme.primary,
                    painter = painterResource(id = R.drawable.ic_insights_24),
                    contentDescription = null
                )
            },
            placeholder = {
                Text(text = "Enter your inspirational word")
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                disabledBorderColor = MaterialTheme.colorScheme.primary,
            ),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TriStateCheckbox(state = checkedParentState,
                onClick = {
                    val state = checkedParentState != ToggleableState.On
                    checkBoxItems.component1().onCheckedChange(state)
                    checkBoxItems.component2().onCheckedChange(state)
                    checkBoxItems.component3().onCheckedChange(state)
                    checkBoxItems.component4().onCheckedChange(state)
                    checkBoxItems.component5().onCheckedChange(state)
                }
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = "Interests"
            )
        }
        checkBoxItems.forEach { checkBoxItem ->
            Row(
                modifier = modifier.fillMaxWidth().padding(start = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = checkBoxItem.checked,
                    onCheckedChange = checkBoxItem.onCheckedChange
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = checkBoxItem.title
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (fullName.isBlank()) {
                    scope.launch {
                        snackbarHostState.showSnackbar(message = context.getString(R.string.invalid_full_name_message))
                    }
                    return@Button
                }
                if (username.isBlank()) {
                    scope.launch {
                        snackbarHostState.showSnackbar(message = context.getString(R.string.invalid_username_message))
                    }
                    return@Button
                }
                if (job.isBlank()) {
                    scope.launch {
                        snackbarHostState.showSnackbar(message = context.getString(R.string.invalid_job_message))
                    }
                    return@Button
                }
                if (experienceLevelOption.isBlank()) {
                    scope.launch {
                        snackbarHostState.showSnackbar(message = context.getString(R.string.invalid_experience_message))
                    }
                    return@Button
                }
                if (isCheckedEmpty) {
                    scope.launch {
                        snackbarHostState.showSnackbar(message = "you must select at least one of the interests")
                    }
                    return@Button
                }
                onCreateProfile()
                keyboardController?.hide()
            },
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
        ) {
            if (!isLoading)
                Text(text = "Continue")
            else CircularProgressIndicator(
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .size(24.dp)
            )
        }
    }
}

data class CheckBoxItem(
    val title: String,
    val checked: Boolean,
    val onCheckedChange: (Boolean) -> Unit
)