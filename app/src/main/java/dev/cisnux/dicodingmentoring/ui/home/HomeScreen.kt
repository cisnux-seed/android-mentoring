package dev.cisnux.dicodingmentoring.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.cisnux.dicodingmentoring.ui.components.SearchBarButton
import dev.cisnux.dicodingmentoring.ui.MainViewModel
import dev.cisnux.dicodingmentoring.ui.theme.DicodingMentoringTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    drawerState: DrawerState,
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val oneTimeUpdateState by rememberUpdatedState(mainViewModel::updateBottomState)
    LaunchedEffect(Unit){
        oneTimeUpdateState(true)
    }
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val coroutineScope = rememberCoroutineScope()
    var expanded by remember { mutableStateOf(false) }


    HomeContent(
        snackbarHostState = snackbarHostState,
        coroutineScope = coroutineScope,
        drawerState = drawerState,
        query = "",
        onSearch = {},
        onOpenSearchBar = {
            expanded = true
            mainViewModel.updateBottomState(false)
        },
        expanded = expanded,
        onCloseSearchBar = {
            expanded = false
            mainViewModel.updateBottomState(true)
        },
        modifier = modifier,
        body = {
            HomeBody()
        },
    )
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun HomeContentPreview() {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    var expanded by remember { mutableStateOf(false) }

    Surface {
        DicodingMentoringTheme {
            HomeContent(
                snackbarHostState = snackbarHostState,
                coroutineScope = coroutineScope,
                drawerState = DrawerState(DrawerValue.Closed),
                query = "",
                onSearch = {},
                onOpenSearchBar = {
                    expanded = true
                },
                expanded = expanded,
                onCloseSearchBar = {
                    expanded = false
                },
                body = {
                    HomeBody()
                }
            )
        }
    }
}

@Composable
fun HomeContent(
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    drawerState: DrawerState,
    onOpenSearchBar: () -> Unit,
    query: String,
    onSearch: (query: String) -> Unit,
    expanded: Boolean,
    onCloseSearchBar: () -> Unit,
    body: @Composable (innerPadding: PaddingValues) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            SearchBarButton(
                modifier = Modifier.padding(16.dp),
                onMenuClick = {
                    coroutineScope.launch {
                        drawerState.open()
                    }
                },
                query = query,
                onSearch = onSearch,
                onOpenSearchBar = onOpenSearchBar,
                expanded = expanded,
                onCloseSearchBar = onCloseSearchBar
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        body(innerPadding)
    }
}

@Preview(showBackground = true)
@Composable
fun HomeBodyPreview() {
    Surface {
        DicodingMentoringTheme {
            HomeBody()
        }
    }
}

@Composable
fun HomeBody() {
}
