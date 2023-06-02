package dev.cisnux.dicodingmentoring.ui.myprofile

import android.content.Context
import android.content.res.Configuration
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.cisnux.dicodingmentoring.R
import dev.cisnux.dicodingmentoring.domain.models.Expertise
import dev.cisnux.dicodingmentoring.domain.models.Review
import dev.cisnux.dicodingmentoring.ui.MainViewModel
import dev.cisnux.dicodingmentoring.ui.theme.DicodingMentoringTheme
import dev.cisnux.dicodingmentoring.utils.UiState
import dev.cisnux.dicodingmentoring.utils.getLearningPathIcon

@Composable
fun MyProfileScreen(
    mainViewModel: MainViewModel,
    navigateToAddMentor: (id: String) -> Unit,
    modifier: Modifier = Modifier,
    myProfileViewModel: MyProfileViewModel = hiltViewModel(),
) {
    val oneTimeUpdateState by rememberUpdatedState(mainViewModel::updateBottomState)
    LaunchedEffect(Unit) {
        oneTimeUpdateState(true)
    }
    val isRefreshContent by mainViewModel.isRefreshMyProfileContent
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val myProfileState by myProfileViewModel.myProfileState
    val isMentorValid by myProfileViewModel.isMentorValid
    val currentUserId by myProfileViewModel.currentUserId

    if(isRefreshContent){
        myProfileViewModel.getUserProfile(currentUserId)
        // reset
        mainViewModel.refreshMyProfileContent(false)
    }

    MyProfileContent(
        snackbarHostState = snackbarHostState,
        isMentorValid = isMentorValid,
        onFabClick = {
            currentUserId.let(navigateToAddMentor)
        },
        modifier = modifier,
    ) { innerPadding ->
        when (myProfileState) {
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
                        about = it.about,
                        photoProfile = it.photoProfileUrl,
                        isMentor = it.isMentorValid,
                        modifier = Modifier.padding(innerPadding),
                        context = context,
                        reviews = listOf(
                            Review(
                                fullName = "Eren Jaeger",
                                comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce finibus leo id enim feugiat, sed tempor erat lacinia. Vestibulum luctus, nisl non sagittis interdum, quam velit condimentum purus, ac pulvinar orci quam id metus.",
                                rating = 4.5f,
                            ),
                            Review(
                                fullName = "Erwin Smith",
                                comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce finibus leo id enim feugiat, sed tempor erat lacinia. Vestibulum luctus, nisl non sagittis interdum, quam velit condimentum purus, ac pulvinar orci quam id metus.",
                                rating = 4.5f,
                            ),
                            Review(
                                fullName = "Levi Ackerman",
                                comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce finibus leo id enim feugiat, sed tempor erat lacinia. Vestibulum luctus, nisl non sagittis interdum, quam velit condimentum purus, ac pulvinar orci quam id metus.",
                                rating = 4.5f,
                            ),
                            Review(
                                fullName = "Mikassa Ackerman",
                                comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce finibus leo id enim feugiat, sed tempor erat lacinia. Vestibulum luctus, nisl non sagittis interdum, quam velit condimentum purus, ac pulvinar orci quam id metus.",
                                rating = 4.5f,
                            ),
                            Review(
                                fullName = "Armin",
                                comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce finibus leo id enim feugiat, sed tempor erat lacinia. Vestibulum luctus, nisl non sagittis interdum, quam velit condimentum purus, ac pulvinar orci quam id metus.",
                                rating = 4.5f,
                            ),
                            Review(
                                fullName = "Zeke Jaeger",
                                comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce finibus leo id enim feugiat, sed tempor erat lacinia. Vestibulum luctus, nisl non sagittis interdum, quam velit condimentum purus, ac pulvinar orci quam id metus.",
                                rating = 4.5f,
                            ),
                            Review(
                                fullName = "Reiner",
                                comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce finibus leo id enim feugiat, sed tempor erat lacinia. Vestibulum luctus, nisl non sagittis interdum, quam velit condimentum purus, ac pulvinar orci quam id metus.",
                                rating = 4.5f,
                            ),
                        ),
                        expertises = it.expertises
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
            else ->{}
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
                isMentorValid = false,
                onFabClick = {}
            ) {
                MyProfileBody(
                    fullName = "Exodus Trivellan",
                    job = "Software Engineer at Apple",
                    email = "exodusjack@gmail.com",
                    username = "exoduse123",
                    about = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce finibus leo id enim feugiat, sed tempor erat lacinia. Vestibulum luctus, nisl non sagittis interdum, quam velit condimentum purus, ac pulvinar orci quam id metus. Donec nec tortor a dolor consectetur tincidunt eu sit amet elit. Nulla convallis ligula et nisl hendrerit, id ultrices elit malesuada. In tincidunt risus in arcu tempor, id malesuada metus congue.",
                    photoProfile = null,
                    modifier = Modifier.padding(it),
                    context = LocalContext.current,
                    isMentor = true,
                    reviews = listOf(
                        Review(
                            fullName = "Eren Jaeger",
                            comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce finibus leo id enim feugiat, sed tempor erat lacinia. Vestibulum luctus, nisl non sagittis interdum, quam velit condimentum purus, ac pulvinar orci quam id metus.",
                            rating = 4.5f,
                        ),
                        Review(
                            fullName = "Erwin Smith",
                            comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce finibus leo id enim feugiat, sed tempor erat lacinia. Vestibulum luctus, nisl non sagittis interdum, quam velit condimentum purus, ac pulvinar orci quam id metus.",
                            rating = 4.5f,
                        ),
                        Review(
                            fullName = "Levi Ackerman",
                            comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce finibus leo id enim feugiat, sed tempor erat lacinia. Vestibulum luctus, nisl non sagittis interdum, quam velit condimentum purus, ac pulvinar orci quam id metus.",
                            rating = 4.5f,
                        ),
                        Review(
                            fullName = "Mikassa Ackerman",
                            comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce finibus leo id enim feugiat, sed tempor erat lacinia. Vestibulum luctus, nisl non sagittis interdum, quam velit condimentum purus, ac pulvinar orci quam id metus.",
                            rating = 4.5f,
                        ),
                        Review(
                            fullName = "Armin",
                            comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce finibus leo id enim feugiat, sed tempor erat lacinia. Vestibulum luctus, nisl non sagittis interdum, quam velit condimentum purus, ac pulvinar orci quam id metus.",
                            rating = 4.5f,
                        ),
                        Review(
                            fullName = "Zeke Jaeger",
                            comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce finibus leo id enim feugiat, sed tempor erat lacinia. Vestibulum luctus, nisl non sagittis interdum, quam velit condimentum purus, ac pulvinar orci quam id metus.",
                            rating = 4.5f,
                        ),
                        Review(
                            fullName = "Reiner",
                            comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce finibus leo id enim feugiat, sed tempor erat lacinia. Vestibulum luctus, nisl non sagittis interdum, quam velit condimentum purus, ac pulvinar orci quam id metus.",
                            rating = 4.5f,
                        ),
                    ),
                    expertises = emptyList()
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
                isMentorValid = false,
                onFabClick = {}
            ) {
                MyProfileBody(
                    fullName = "Exodus Trivellan",
                    job = "Software Engineer at Apple",
                    email = "exodusjack@gmail.com",
                    username = "exoduse123",
                    about = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce finibus leo id enim feugiat, sed tempor erat lacinia. Vestibulum luctus, nisl non sagittis interdum, quam velit condimentum purus, ac pulvinar orci quam id metus. Donec nec tortor a dolor consectetur tincidunt eu sit amet elit. Nulla convallis ligula et nisl hendrerit, id ultrices elit malesuada. In tincidunt risus in arcu tempor, id malesuada metus congue.",
                    photoProfile = "https://i.pinimg.com/originals/50/d4/29/50d429ea5c9afe0ef9cb3c96f784bea4.jpg",
                    modifier = Modifier.padding(it),
                    context = LocalContext.current,
                    isMentor = false,
                    reviews = listOf(
                        Review(
                            fullName = "Eren Jaeger",
                            comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce finibus leo id enim feugiat, sed tempor erat lacinia. Vestibulum luctus, nisl non sagittis interdum, quam velit condimentum purus, ac pulvinar orci quam id metus.",
                            rating = 4.5f,
                        ),
                        Review(
                            fullName = "Erwin Smith",
                            comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce finibus leo id enim feugiat, sed tempor erat lacinia. Vestibulum luctus, nisl non sagittis interdum, quam velit condimentum purus, ac pulvinar orci quam id metus.",
                            rating = 4.5f,
                        ),
                        Review(
                            fullName = "Levi Ackerman",
                            comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce finibus leo id enim feugiat, sed tempor erat lacinia. Vestibulum luctus, nisl non sagittis interdum, quam velit condimentum purus, ac pulvinar orci quam id metus.",
                            rating = 4.5f,
                        ),
                        Review(
                            fullName = "Mikassa Ackerman",
                            comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce finibus leo id enim feugiat, sed tempor erat lacinia. Vestibulum luctus, nisl non sagittis interdum, quam velit condimentum purus, ac pulvinar orci quam id metus.",
                            rating = 4.5f,
                        ),
                        Review(
                            fullName = "Armin",
                            comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce finibus leo id enim feugiat, sed tempor erat lacinia. Vestibulum luctus, nisl non sagittis interdum, quam velit condimentum purus, ac pulvinar orci quam id metus.",
                            rating = 4.5f,
                        ),
                        Review(
                            fullName = "Zeke Jaeger",
                            comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce finibus leo id enim feugiat, sed tempor erat lacinia. Vestibulum luctus, nisl non sagittis interdum, quam velit condimentum purus, ac pulvinar orci quam id metus.",
                            rating = 4.5f,
                        ),
                        Review(
                            fullName = "Reiner",
                            comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce finibus leo id enim feugiat, sed tempor erat lacinia. Vestibulum luctus, nisl non sagittis interdum, quam velit condimentum purus, ac pulvinar orci quam id metus.",
                            rating = 4.5f,
                        ),
                    ),
                    expertises = emptyList()
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
                onFabClick = {},
                isMentorValid = false
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

@Composable
fun MyProfileContent(
    snackbarHostState: SnackbarHostState,
    onFabClick: () -> Unit,
    isMentorValid: Boolean,
    modifier: Modifier = Modifier,
    body: @Composable (innerPadding: PaddingValues) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        floatingActionButton = {
            if (!isMentorValid) {
                ExtendedFloatingActionButton(
                    onClick = onFabClick,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
                        Text(text = "JOIN US", style = MaterialTheme.typography.labelLarge)
                    }
                }
            }
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
                job = "Computer Engineering Student at Telkom University",
                email = "exodusjack@gmail.com",
                username = "exoduse123",
                about = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce finibus leo id enim feugiat, sed tempor erat lacinia. Vestibulum luctus, nisl non sagittis interdum, quam velit condimentum purus, ac pulvinar orci quam id metus. Donec nec tortor a dolor consectetur tincidunt eu sit amet elit. Nulla convallis ligula et nisl hendrerit, id ultrices elit malesuada. In tincidunt risus in arcu tempor, id malesuada metus congue.",
                photoProfile = "https://i.pinimg.com/originals/50/d4/29/50d429ea5c9afe0ef9cb3c96f784bea4.jpg",
                context = LocalContext.current,
                isMentor = false,
                reviews = listOf(
                    Review(
                        fullName = "Eren Jaeger",
                        comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce finibus leo id enim feugiat, sed tempor erat lacinia. Vestibulum luctus, nisl non sagittis interdum, quam velit condimentum purus, ac pulvinar orci quam id metus.",
                        rating = 4.5f,
                    ),
                    Review(
                        fullName = "Erwin Smith",
                        comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce finibus leo id enim feugiat, sed tempor erat lacinia. Vestibulum luctus, nisl non sagittis interdum, quam velit condimentum purus, ac pulvinar orci quam id metus.",
                        rating = 4.5f,
                    ),
                    Review(
                        fullName = "Levi Ackerman",
                        comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce finibus leo id enim feugiat, sed tempor erat lacinia. Vestibulum luctus, nisl non sagittis interdum, quam velit condimentum purus, ac pulvinar orci quam id metus.",
                        rating = 4.5f,
                    ),
                    Review(
                        fullName = "Mikassa Ackerman",
                        comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce finibus leo id enim feugiat, sed tempor erat lacinia. Vestibulum luctus, nisl non sagittis interdum, quam velit condimentum purus, ac pulvinar orci quam id metus.",
                        rating = 4.5f,
                    ),
                    Review(
                        fullName = "Armin",
                        comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce finibus leo id enim feugiat, sed tempor erat lacinia. Vestibulum luctus, nisl non sagittis interdum, quam velit condimentum purus, ac pulvinar orci quam id metus.",
                        rating = 4.5f,
                    ),
                    Review(
                        fullName = "Zeke Jaeger",
                        comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce finibus leo id enim feugiat, sed tempor erat lacinia. Vestibulum luctus, nisl non sagittis interdum, quam velit condimentum purus, ac pulvinar orci quam id metus.",
                        rating = 4.5f,
                    ),
                    Review(
                        fullName = "Reiner",
                        comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce finibus leo id enim feugiat, sed tempor erat lacinia. Vestibulum luctus, nisl non sagittis interdum, quam velit condimentum purus, ac pulvinar orci quam id metus.",
                        rating = 4.5f,
                    ),
                ),
                expertises = listOf(
                    Expertise(
                        learningPath = "Android",
                        experienceLevel = "Beginner",
                        skills = listOf(
                            "Object-Oriented Programming",
                            "Room",
                            "Jetpack Compose"
                        ),
                        certificates = listOf(
                            "https://www.google.com",
                            "https://www.google.com",
                            "https://www.google.com",
                            "https://www.google.com",
                            "https://www.google.com",
                        )
                    ),
                    Expertise(
                        learningPath = "iOS",
                        experienceLevel = "Expert",
                        skills = listOf(
                            "Object-Oriented Programming",
                            "Room",
                            "Jetpack Compose"
                        ), certificates = listOf(
                            "https://www.google.com",
                            "https://www.google.com",
                            "https://www.google.com",
                            "https://www.google.com",
                            "https://www.google.com",
                        )
                    ),
                    Expertise(
                        learningPath = "Front-End",
                        experienceLevel = "Intermediate",
                        skills = listOf(
                            "Object-Oriented Programming",
                            "Room",
                            "Jetpack Compose"
                        ), certificates = listOf(
                            "https://www.google.com",
                            "https://www.google.com",
                            "https://www.google.com",
                            "https://www.google.com",
                            "https://www.google.com",
                        )
                    ),
                    Expertise(
                        learningPath = "Machine Learning",
                        experienceLevel = "Beginner",
                        skills = listOf(
                            "Object-Oriented Programming",
                            "Room",
                            "Jetpack Compose"
                        ),
                        certificates = listOf(
                            "https://www.google.com",
                            "https://www.google.com",
                            "https://www.google.com",
                            "https://www.google.com",
                            "https://www.google.com",
                        )
                    ),
                )
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
    about: String,
    isMentor: Boolean,
    modifier: Modifier = Modifier,
    expertises: List<Expertise>,
    reviews: List<Review>,
    context: Context,
    photoProfile: String? = null,
) {
    val tabTitles = listOf("Overview", "Reviews")
    var selectedTab by rememberSaveable {
        mutableStateOf(0)
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = modifier
                .padding(top = 16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            photoProfile?.let {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(photoProfile)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(id = R.drawable.circle_avatar_loading_placeholder),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .border(2.dp, Color.White, CircleShape)
                        .shadow(2.dp, CircleShape, true)
                        .clip(CircleShape)
                        .size(110.dp)
                )
            } ?: Box(
                modifier = Modifier
                    .border(2.dp, Color.White, CircleShape)
                    .shadow(2.dp, CircleShape, true)
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = CircleShape
                    )
                    .size(110.dp)
            ) {
                Text(
                    text = fullName[0].toString(),
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = fullName,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = job,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "$email | @$username",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TabRow(selectedTabIndex = selectedTab) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = {
                            selectedTab = index
                        }) {
                        Text(text = title, style = MaterialTheme.typography.titleSmall)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
            when (selectedTab) {
                0 -> Overview(
                    about = about,
                    isMentor = isMentor,
                    expertises = expertises
                )

                1 -> ReviewWithList(reviews = reviews, context = context)
            }
        }
        IconButton(onClick = { /*TODO*/ }, modifier = Modifier.align(Alignment.TopEnd)) {
            Icon(
                imageVector = Icons.Default.Edit,
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = null
            )
        }
    }

}

@Composable
fun ReviewWithList(reviews: List<Review>, modifier: Modifier = Modifier, context: Context) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        content = {
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
            items(reviews) { review ->
                ReviewCard(
                    fullName = review.fullName,
                    comment = review.comment,
                    context = context,
                    rating = review.rating
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    )
}

@Composable
fun Overview(
    about: String, isMentor: Boolean,
    modifier: Modifier = Modifier,
    expertises: List<Expertise>
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "About Me",
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
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Status",
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = if (isMentor) "Mentor" else "Mentee",
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        if (expertises.isNotEmpty()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Expertises",
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.titleMedium
                )
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        tint = MaterialTheme.colorScheme.onBackground,
                        contentDescription = null
                    )
                }
            }
            expertises.forEach { expertise ->
                ExpertiseCard(
                    learningPath = expertise.learningPath,
                    skills = expertise.skills,
                    experienceLevel = expertise.experienceLevel
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExpertiseCardPreview() {
    Surface {
        DicodingMentoringTheme {
            ExpertiseCard(
                learningPath = "Android",
                skills = listOf(
                    "Object-Oriented Programming",
                    "Design Patter",
                    "Java",
                    "Kotlin",
                ),
                experienceLevel = "Beginner",
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
fun ExpertiseCard(
    learningPath: String,
    skills: List<String>,
    experienceLevel: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                tint = MaterialTheme.colorScheme.onBackground,
                painter = painterResource(id = getLearningPathIcon(learningPath)),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text("$learningPath - $experienceLevel", style = MaterialTheme.typography.titleMedium)
        }
        Column(
            modifier = Modifier
                .padding(start = 40.dp)
                .fillMaxWidth(), horizontalAlignment = Alignment.Start
        ) {
            skills.forEach { skill ->
                Row {
                    Text("•", style = MaterialTheme.typography.titleSmall)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(skill, style = MaterialTheme.typography.bodyMedium)
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Divider(color = Color.Gray.copy(alpha = 0.4f), thickness = 0.8.dp)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReviewCardPreview() {
    Surface(modifier = Modifier.fillMaxSize()) {
        DicodingMentoringTheme {
            ReviewCard(
                fullName = "Eren Jaeger",
                comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce finibus leo id enim feugiat, sed tempor erat lacinia. Vestibulum luctus, nisl non sagittis interdum, quam velit condimentum purus, ac pulvinar orci quam id metus.",
                context = LocalContext.current,
                rating = 4.7f,
            )
        }
    }
}

@Composable
fun ReviewCard(
    fullName: String,
    comment: String,
    context: Context,
    rating: Float,
    modifier: Modifier = Modifier,
    photoProfile: String? = null,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            photoProfile?.let {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(photoProfile)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(id = R.drawable.circle_avatar_loading_placeholder),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .border(1.dp, Color.White, CircleShape)
                        .clip(CircleShape)
                        .size(30.dp)
                )
            } ?: Box(
                modifier = Modifier
                    .border(1.dp, Color.White, CircleShape)
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = CircleShape
                    )
                    .size(30.dp)
            ) {
                Text(
                    text = fullName[0].toString(),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = fullName, style = MaterialTheme.typography.titleMedium)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = comment,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Star,
                tint = colorResource(id = R.color.dark_yellow),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = rating.toString(),
                maxLines = 1,
                style = MaterialTheme.typography.labelLarge
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        Divider(color = Color.Gray.copy(alpha = 0.4f), thickness = 1.dp)
    }
}

@Composable
fun GridChipInterest(
    count: Int,
    interests: List<String>,
    modifier: Modifier = Modifier
) {
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
