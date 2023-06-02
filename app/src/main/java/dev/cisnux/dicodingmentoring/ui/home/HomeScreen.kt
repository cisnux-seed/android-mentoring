package dev.cisnux.dicodingmentoring.ui.home

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.cisnux.dicodingmentoring.R
import dev.cisnux.dicodingmentoring.domain.models.GetMentor
import dev.cisnux.dicodingmentoring.ui.MainViewModel
import dev.cisnux.dicodingmentoring.ui.components.MentorCard
import dev.cisnux.dicodingmentoring.ui.components.SearchBarButton
import dev.cisnux.dicodingmentoring.ui.theme.DicodingMentoringTheme
import dev.cisnux.dicodingmentoring.utils.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    drawerState: DrawerState,
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val oneTimeUpdateState by rememberUpdatedState(mainViewModel::updateBottomState)
    LaunchedEffect(Unit) {
        oneTimeUpdateState(true)
    }
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var expanded by remember { mutableStateOf(false) }
    val mentorsState by homeViewModel.mentorsState
    val currentUser by homeViewModel.currentUser.collectAsStateWithLifecycle(null)

    HomeContent(
        modifier = modifier,
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
        body = { innerPadding ->
            when (mentorsState) {
                is UiState.Initialize -> currentUser?.uid?.let(homeViewModel::getMentors)
                is UiState.Success -> {
                    HomeBody(
                        context = context,
                        mentors = (mentorsState as UiState.Success<List<GetMentor>>).data!!,
                        modifier = Modifier.padding(innerPadding)
                    )
                }

                is UiState.Error -> {
                    LaunchedEffect(snackbarHostState) {
                        (mentorsState as UiState.Error).error?.message?.let { message ->
                            snackbarHostState.showSnackbar(
                                message
                            )
                        }
                    }
                }

                is UiState.Loading -> {
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
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
                body = { innerPadding ->
                    HomeBody(
                        context = LocalContext.current,
                        mentors = listOf(
                            GetMentor(
                                id = "1",
                                fullName = "Eren Jaeger",
                                photoProfileUrl = "https://upload.wikimedia.org/wikipedia/it/a/a7/Eren_jaeger.png",
                                averageRating = 3.0,
                                job = "Front-End Developer"
                            ),
                            GetMentor(
                                id = "1",
                                fullName = "Eren Jaeger",
                                photoProfileUrl = "https://upload.wikimedia.org/wikipedia/it/a/a7/Eren_jaeger.png",
                                averageRating = 3.0,
                                job = "Front-End Developer"
                            ),
                            GetMentor(
                                id = "1",
                                fullName = "Eren Jaeger",
                                photoProfileUrl = "https://upload.wikimedia.org/wikipedia/it/a/a7/Eren_jaeger.png",
                                averageRating = 3.0,
                                job = "Front-End Developer"
                            ),
                            GetMentor(
                                id = "1",
                                fullName = "Eren Jaeger",
                                photoProfileUrl = "https://upload.wikimedia.org/wikipedia/it/a/a7/Eren_jaeger.png",
                                averageRating = 3.0,
                                job = "Front-End Developer"
                            ),
                            GetMentor(
                                id = "1",
                                fullName = "Eren Jaeger",
                                photoProfileUrl = "https://upload.wikimedia.org/wikipedia/it/a/a7/Eren_jaeger.png",
                                averageRating = 3.0,
                                job = "Front-End Developer"
                            ),
                            GetMentor(
                                id = "1",
                                fullName = "Eren Jaeger",
                                photoProfileUrl = "https://upload.wikimedia.org/wikipedia/it/a/a7/Eren_jaeger.png",
                                averageRating = 3.0,
                                job = "Front-End Developer"
                            ),
                            GetMentor(
                                id = "1",
                                fullName = "Eren Jaeger",
                                photoProfileUrl = "https://upload.wikimedia.org/wikipedia/it/a/a7/Eren_jaeger.png",
                                averageRating = 3.0,
                                job = "Front-End Developer"
                            ),
                            GetMentor(
                                id = "1",
                                fullName = "Eren Jaeger",
                                photoProfileUrl = "https://upload.wikimedia.org/wikipedia/it/a/a7/Eren_jaeger.png",
                                averageRating = 3.0,
                                job = "Front-End Developer"
                            ),
                            GetMentor(
                                id = "1",
                                fullName = "Eren Jaeger",
                                photoProfileUrl = "https://upload.wikimedia.org/wikipedia/it/a/a7/Eren_jaeger.png",
                                averageRating = 3.0,
                                job = "Front-End Developer"
                            ),
                            GetMentor(
                                id = "1",
                                fullName = "Eren Jaeger",
                                photoProfileUrl = "https://upload.wikimedia.org/wikipedia/it/a/a7/Eren_jaeger.png",
                                averageRating = 3.0,
                                job = "Front-End Developer"
                            ),
                            GetMentor(
                                id = "1",
                                fullName = "Eren Jaeger",
                                photoProfileUrl = "https://upload.wikimedia.org/wikipedia/it/a/a7/Eren_jaeger.png",
                                averageRating = 3.0,
                                job = "Front-End Developer"
                            ),
                        ),
                        modifier = Modifier.padding(innerPadding)
                    )
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
            HomeBody(
                context = LocalContext.current,
                mentors = listOf(
                    GetMentor(
                        id = "1",
                        fullName = "Eren Jaeger",
                        photoProfileUrl = "https://upload.wikimedia.org/wikipedia/it/a/a7/Eren_jaeger.png",
                        averageRating = 3.0,
                        job = "Front-End Developer"
                    ),
                    GetMentor(
                        id = "1",
                        fullName = "Eren Jaeger",
                        photoProfileUrl = "https://upload.wikimedia.org/wikipedia/it/a/a7/Eren_jaeger.png",
                        averageRating = 3.0,
                        job = "Front-End Developer"
                    ),
                    GetMentor(
                        id = "1",
                        fullName = "Eren Jaeger",
                        photoProfileUrl = "https://upload.wikimedia.org/wikipedia/it/a/a7/Eren_jaeger.png",
                        averageRating = 3.0,
                        job = "Front-End Developer"
                    ),
                    GetMentor(
                        id = "1",
                        fullName = "Eren Jaeger",
                        photoProfileUrl = "https://upload.wikimedia.org/wikipedia/it/a/a7/Eren_jaeger.png",
                        averageRating = 3.0,
                        job = "Front-End Developer"
                    ),
                    GetMentor(
                        id = "1",
                        fullName = "Eren Jaeger",
                        photoProfileUrl = "https://upload.wikimedia.org/wikipedia/it/a/a7/Eren_jaeger.png",
                        averageRating = 3.0,
                        job = "Front-End Developer"
                    ),
                    GetMentor(
                        id = "1",
                        fullName = "Eren Jaeger",
                        photoProfileUrl = "https://upload.wikimedia.org/wikipedia/it/a/a7/Eren_jaeger.png",
                        averageRating = 3.0,
                        job = "Front-End Developer"
                    ),
                    GetMentor(
                        id = "1",
                        fullName = "Eren Jaeger",
                        photoProfileUrl = "https://upload.wikimedia.org/wikipedia/it/a/a7/Eren_jaeger.png",
                        averageRating = 3.0,
                        job = "Front-End Developer"
                    ),
                    GetMentor(
                        id = "1",
                        fullName = "Eren Jaeger",
                        photoProfileUrl = "https://upload.wikimedia.org/wikipedia/it/a/a7/Eren_jaeger.png",
                        averageRating = 3.0,
                        job = "Front-End Developer"
                    ),
                    GetMentor(
                        id = "1",
                        fullName = "Eren Jaeger",
                        photoProfileUrl = "https://upload.wikimedia.org/wikipedia/it/a/a7/Eren_jaeger.png",
                        averageRating = 3.0,
                        job = "Front-End Developer"
                    ),
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeBodyPreviewWhenEmpty() {
    Surface {
        DicodingMentoringTheme {
            HomeBody(
                context = LocalContext.current,
                mentors = emptyList()
            )
        }
    }
}

@Composable
fun HomeBody(
    mentors: List<GetMentor>,
    context: Context,
    modifier: Modifier = Modifier,
) {
    if (mentors.isEmpty()) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.empty_mentors),
                contentDescription = null,
                modifier = Modifier.size(150.dp),
            )
        }
    } else {
        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(bottom = 16.dp, start = 16.dp, end = 16.dp),
            content = {
                items(mentors) { mentor ->
                    MentorCard(
                        fullName = mentor.fullName,
                        photoProfile = mentor.photoProfileUrl,
                        job = mentor.job,
                        averageRating = mentor.averageRating,
                        context = context,
                        onClick = {}
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Divider(
                        color = Color.Gray.copy(alpha = 0.6f),
                        thickness = 0.5.dp,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        )
    }
}
