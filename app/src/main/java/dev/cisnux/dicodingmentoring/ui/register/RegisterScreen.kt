package dev.cisnux.dicodingmentoring.ui.register

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.cisnux.dicodingmentoring.R
import dev.cisnux.dicodingmentoring.ui.components.AuthForm
import dev.cisnux.dicodingmentoring.ui.theme.DicodingMentoringTheme
import dev.cisnux.dicodingmentoring.utils.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun RegisterBodyPreview() {
    DicodingMentoringTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val email = ""
            val password = ""
            val isValidEmail = true
            val isValidPassword = true

            val scope = rememberCoroutineScope()
            val snackbarHostState = remember {
                SnackbarHostState()
            }
            val context = LocalContext.current

            RegisterBody(
                email = email,
                password = password,
                onEmailQueryChanged = {},
                onPasswordQueryChanged = {},
                onAuthEmailPassword = { /*TODO*/ },
                authButtonTitle = stringResource(id = R.string.sign_up_title_button),
                isValidEmail = isValidEmail,
                isValidPassword = isValidPassword,
                context = context,
                snackbarHostState = snackbarHostState,
                scope = scope,
                navigateToSignIn = {},
                isPasswordVisible = true,
                isLoading = false,
                onPasswordVisible = {},
                keyboardController = LocalSoftwareKeyboardController.current
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun RegisterScreen(
    navigateToHome: (uid: String) -> Unit,
    navigateToLogin: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = hiltViewModel(),
) {
    val email by viewModel.email
    val password by viewModel.password
    val isValidEmail by viewModel.isValidEmail
    val isValidPassword by viewModel.isValidPassword
    val isPasswordVisible by viewModel.isPasswordVisible
    val isLoading by viewModel.isLoading

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val registerState by viewModel.registerState.collectAsStateWithLifecycle(UiState.Loading)

    when (registerState) {
        is UiState.Success -> {
            (registerState as UiState.Success).data?.uid?.let(navigateToHome)
        }

        is UiState.Error -> {
            (registerState as UiState.Error).error?.message?.let { message ->
                LaunchedEffect(snackbarHostState) {
                    snackbarHostState.showSnackbar(message)
                }
            }
        }

        else -> {}
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        RegisterBody(
            email = email,
            password = password,
            onEmailQueryChanged = viewModel::onEmailQueryChanged,
            onPasswordQueryChanged = viewModel::onPasswordQueryChanged,
            onAuthEmailPassword = { viewModel.register() },
            authButtonTitle = stringResource(id = R.string.sign_up_title_button),
            isValidEmail = isValidEmail,
            isValidPassword = isValidPassword,
            context = context,
            snackbarHostState = snackbarHostState,
            scope = scope,
            modifier = modifier.padding(innerPadding),
            navigateToSignIn = navigateToLogin,
            isPasswordVisible = isPasswordVisible,
            isLoading = isLoading,
            onPasswordVisible = viewModel::onPasswordVisible,
            keyboardController = keyboardController
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterBody(
    email: String,
    password: String,
    onEmailQueryChanged: (email: String) -> Unit,
    onPasswordQueryChanged: (password: String) -> Unit,
    onAuthEmailPassword: () -> Unit,
    authButtonTitle: String,
    isValidEmail: Boolean,
    isValidPassword: Boolean,
    context: Context,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    keyboardController: SoftwareKeyboardController?,
    navigateToSignIn: () -> Unit,
    isLoading: Boolean,
    isPasswordVisible: Boolean,
    onPasswordVisible: (visible: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    AuthForm(
        email = email,
        password = password,
        onEmailQueryChanged = onEmailQueryChanged,
        onPasswordQueryChanged = onPasswordQueryChanged,
        isValidEmail = isValidEmail,
        isValidPassword = isValidPassword,
        modifier = modifier,
        isPasswordVisible = isPasswordVisible,
        onPasswordVisible = onPasswordVisible
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = {
                if (!isValidEmail) {
                    scope.launch {
                        snackbarHostState.showSnackbar(message = context.getString(R.string.invalid_email_message))
                    }
                    return@Button
                }
                if (!isValidPassword) {
                    scope.launch {
                        snackbarHostState.showSnackbar(message = context.getString(R.string.invalid_password_message))
                    }
                    return@Button
                }
                onAuthEmailPassword()
                keyboardController?.hide()
            },
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
        ) {
            if (!isLoading)
                Text(text = authButtonTitle)
            else CircularProgressIndicator(
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .size(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
                    append(stringResource(R.string.signin_title_action))
                }
                withStyle(style = SpanStyle(color = Color.Blue)) {
                    append(stringResource(R.string.sign_in))
                }
            },
            textAlign = TextAlign.Center,
            modifier = Modifier.clickable(onClick = navigateToSignIn).fillMaxWidth(),
            style = MaterialTheme.typography.labelLarge
        )
    }
}