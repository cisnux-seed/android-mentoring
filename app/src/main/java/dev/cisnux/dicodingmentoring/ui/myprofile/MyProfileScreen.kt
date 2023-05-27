package dev.cisnux.dicodingmentoring.ui.myprofile

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.cisnux.dicodingmentoring.R
import dev.cisnux.dicodingmentoring.ui.theme.DicodingMentoringTheme
import dev.cisnux.dicodingmentoring.utils.UiState

@Composable
fun MyProfileScreen(
    viewModel: MyProfileViewModel = hiltViewModel()
) {
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val myProfileState by viewModel.myProfileState

    MyProfileContent(snackbarHostState = snackbarHostState) { innerPadding ->
        when (myProfileState) {
            is UiState.Initialize -> {
                viewModel.getUserProfile()
            }

            is UiState.Error -> {
                (myProfileState as UiState.Error).error?.let { exception ->
                    LaunchedEffect(snackbarHostState) {
                        exception.message?.let { snackbarHostState.showSnackbar(it) }
                    }
                }
            }

            is UiState.Success -> {
                val context = LocalContext.current
                val userProfile = (myProfileState as UiState.Success).data
                userProfile?.let {
                    MyProfileBody(
                        fullName = it.fullName,
                        job = it.job,
                        email = it.email,
                        username = it.username,
                        experienceLevel = it.experienceLevel,
                        interests = it.interests,
                        about = it.about,
                        photoProfile = it.photoProfileUrl,
                        isMentor = it.isMentor,
                        scrollState = rememberScrollState(),
                        modifier = Modifier.padding(innerPadding),
                        context = context
                    )
                }
            }

            is UiState.Loading -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(48.dp))
                }
            }
        }

    }
}

@Preview(
    showSystemUi = true, showBackground = true,
    device = "spec:parent=pixel_6_pro,orientation=landscape"
)
@Composable
fun MyProfileContentLandscapePreviewWhenSuccess() {
    DicodingMentoringTheme {
        Surface {
            MyProfileContent(
                snackbarHostState = SnackbarHostState(),
            ) {
                MyProfileBody(
                    fullName = "Exodus Trivellan",
                    job = "Software Engineer at Apple",
                    email = "exodusjack@gmail.com",
                    username = "exoduse123",
                    experienceLevel = "Beginner",
                    interests = listOf(
                        stringResource(id = R.string.android),
                        stringResource(id = R.string.ios),
                        stringResource(id = R.string.frontend),
                        stringResource(id = R.string.backend),
                        stringResource(id = R.string.cloud_computing),
                        stringResource(id = R.string.machine_learning),
                        stringResource(id = R.string.ui_ux),
                    ),
                    about = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce finibus leo id enim feugiat, sed tempor erat lacinia. Vestibulum luctus, nisl non sagittis interdum, quam velit condimentum purus, ac pulvinar orci quam id metus. Donec nec tortor a dolor consectetur tincidunt eu sit amet elit. Nulla convallis ligula et nisl hendrerit, id ultrices elit malesuada. In tincidunt risus in arcu tempor, id malesuada metus congue.",
                    photoProfile = "https://i.pinimg.com/originals/50/d4/29/50d429ea5c9afe0ef9cb3c96f784bea4.jpg",
                    scrollState = rememberScrollState(),
                    modifier = Modifier.padding(it),
                    context = LocalContext.current,
                    isMentor = true,
                )
            }
        }
    }
}

@Preview(
    showSystemUi = true, showBackground = true,
    device = "spec:parent=pixel_6_pro,orientation=portrait"
)
@Composable
fun MyProfileContentPortraitPreviewWhenSuccess() {
    DicodingMentoringTheme {
        Surface {
            MyProfileContent(
                snackbarHostState = SnackbarHostState(),
            ) {
                MyProfileBody(
                    fullName = "Exodus Trivellan",
                    job = "Software Engineer at Apple",
                    email = "exodusjack@gmail.com",
                    username = "exoduse123",
                    experienceLevel = "Expert",
                    interests = listOf(
                        stringResource(id = R.string.android),
                        stringResource(id = R.string.ios),
                        stringResource(id = R.string.frontend),
                        stringResource(id = R.string.backend),
                        stringResource(id = R.string.cloud_computing),
                        stringResource(id = R.string.machine_learning),
                        stringResource(id = R.string.ui_ux),
                    ),
                    about = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce finibus leo id enim feugiat, sed tempor erat lacinia. Vestibulum luctus, nisl non sagittis interdum, quam velit condimentum purus, ac pulvinar orci quam id metus. Donec nec tortor a dolor consectetur tincidunt eu sit amet elit. Nulla convallis ligula et nisl hendrerit, id ultrices elit malesuada. In tincidunt risus in arcu tempor, id malesuada metus congue.",
                    photoProfile = "https://i.pinimg.com/originals/50/d4/29/50d429ea5c9afe0ef9cb3c96f784bea4.jpg",
                    scrollState = rememberScrollState(),
                    modifier = Modifier.padding(it),
                    context = LocalContext.current,
                    isMentor = false,
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun MyProfileContentPreviewWhenLoading() {
    DicodingMentoringTheme {
        Surface {
            MyProfileContent(
                snackbarHostState = SnackbarHostState(),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(48.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyProfileContent(
    snackbarHostState: SnackbarHostState,
    body: @Composable (innerPadding: PaddingValues) -> Unit,
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null
                )
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        body(innerPadding)
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
    showSystemUi = true
)
@Composable
fun MyProfileBodyPreview() {
    DicodingMentoringTheme {
        Surface {
            MyProfileBody(
                fullName = "Exodus Trivellan",
                job = "Software Engineer at Apple",
                email = "exodusjack@gmail.com",
                username = "exoduse123",
                experienceLevel = "Intermediate",
                interests = listOf(
                    stringResource(id = R.string.android),
                    stringResource(id = R.string.ios),
                    stringResource(id = R.string.frontend),
                    stringResource(id = R.string.backend),
                    stringResource(id = R.string.cloud_computing),
                    stringResource(id = R.string.machine_learning),
                    stringResource(id = R.string.ui_ux),
                ),
                about = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce finibus leo id enim feugiat, sed tempor erat lacinia. Vestibulum luctus, nisl non sagittis interdum, quam velit condimentum purus, ac pulvinar orci quam id metus. Donec nec tortor a dolor consectetur tincidunt eu sit amet elit. Nulla convallis ligula et nisl hendrerit, id ultrices elit malesuada. In tincidunt risus in arcu tempor, id malesuada metus congue.",
                photoProfile = "https://i.pinimg.com/originals/50/d4/29/50d429ea5c9afe0ef9cb3c96f784bea4.jpg",
                scrollState = rememberScrollState(),
                context = LocalContext.current,
                isMentor = false,
            )
        }
    }
}

@Composable
fun MyProfileBody(
    fullName: String,
    job: String,
    email: String,
    username: String,
    experienceLevel: String,
    about: String,
    interests: List<String>,
    isMentor: Boolean,
    scrollState: ScrollState,
    modifier: Modifier = Modifier,
    context: Context,
    photoProfile: String? = null,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Box(
            content = {},
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.secondaryContainer,
                            MaterialTheme.colorScheme.tertiaryContainer,
                            MaterialTheme.colorScheme.primaryContainer,
                        )
                    )
                )
                .fillMaxWidth()
                .height(130.dp)
        )
        Column(
            modifier = modifier
                .padding(PaddingValues(bottom = 75.dp))
                .fillMaxSize()
                .offset(y = 70.dp)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(photoProfile)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(id = R.drawable.avatar_loading_placeholder),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .border(2.dp, Color.White, CircleShape)
                        .shadow(2.dp, CircleShape, true)
                        .clip(CircleShape)
                        .size(110.dp)
                )
                FilledTonalButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .width(130.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
                        Text(text = "JOIN US")
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = fullName,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = job,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "$email | @$username",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.labelLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            GridChipInterest(count = 4, interests = interests)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Status",
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (isMentor) "Mentor/Mentee" else "Mentee",
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Experience Level",
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = experienceLevel,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "About",
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = about,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun GridChipInterest(count: Int, interests: List<String>, modifier: Modifier = Modifier) {
    val gridItems = interests.chunked(count)

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .fillMaxWidth()
    ) {
        gridItems.forEach {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                it.forEach { interest ->
                    Text(
                        text = interest,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                        maxLines = 1,
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                            .background(
                                color = MaterialTheme.colorScheme.secondaryContainer,
                                shape = MaterialTheme.shapes.extraSmall
                            )
                            .clip(CircleShape)
                            .padding(horizontal = 4.dp, vertical = 2.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }
        }
    }
}
