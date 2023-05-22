package dev.cisnux.dicodingmentoring.ui.home

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.cisnux.dicodingmentoring.utils.UiState

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        HomeScreen(navigateToLogin = {}, navigateToRegisterProfile = {})
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToLogin: () -> Unit,
    navigateToRegisterProfile: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val isLogin by viewModel.isLogin
    val isProfileExist by viewModel.isProfileExist
    val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()
    val userProfileState by viewModel.userProfileState.collectAsState()
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    if (!isLogin) {
        navigateToLogin()
    } else {
        if (userProfileState is UiState.Error) {
            (userProfileState as UiState.Error).error?.message?.let { message ->
                LaunchedEffect(snackbarHostState) {
                    snackbarHostState.showSnackbar(message)
                }
                Log.d("HomeScreen", message)
                currentUser?.uid?.let(navigateToRegisterProfile)
            }
        }

        Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { innerPadding ->

            Surface(
                modifier = modifier
                    .padding(innerPadding)
                    .fillMaxSize(), color = MaterialTheme.colorScheme.background
            ) {
                Text(text = "Hello World")
            }
        }
    }

}