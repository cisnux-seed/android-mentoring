package dev.cisnux.dicodingmentoring.ui.chatroom

import android.content.Context
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.cisnux.dicodingmentoring.R
import dev.cisnux.dicodingmentoring.data.realtime.Chat
import dev.cisnux.dicodingmentoring.ui.MainViewModel
import dev.cisnux.dicodingmentoring.ui.theme.DicodingMentoringTheme
import dev.cisnux.dicodingmentoring.utils.withTimeFormat

@Composable
fun RoomChatScreen(
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier,
    fullName: String,
    email: String,
    photoProfile: String?,
    navigateUp: () -> Unit,
    chatRoomViewModel: ChatRoomViewModel = hiltViewModel()
) {
    val oneTimeUpdateState by rememberUpdatedState(mainViewModel::updateBottomState)
    LaunchedEffect(Unit) {
        oneTimeUpdateState(false)
    }
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val shouldShowConnectionError by chatRoomViewModel.showConnectionError
    val realtimeChats by chatRoomViewModel.realtimeChats
    val message by chatRoomViewModel.message
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val currentUserId by chatRoomViewModel.currentUserId

    if (shouldShowConnectionError) {
        LaunchedEffect(snackbarHostState) {
            snackbarHostState.showSnackbar("no internet connection")
        }
    }

    ChatRoomContent(
        fullName = fullName,
        email = email,
        modifier = modifier,
        context = context,
        navigateUp = navigateUp,
        photoProfile = photoProfile,
        snackbarHostState = snackbarHostState,
        body = { innerPadding ->
            ChatRoomBody(
                chats = realtimeChats?.chats ?: listOf(),
                message = message,
                textFieldScrollState = scrollState,
                onMessageChanged = chatRoomViewModel::onMessageChanged,
                onSender = { senderId ->
                    currentUserId == senderId
                },
                modifier = Modifier.padding(innerPadding),
                onSentMessage = {
                    if (message.isNotBlank()) {
                        chatRoomViewModel.onSentNewMessage()
                    }
                }
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ChatRoomContentPreview() {
    ChatRoomContent(
        context = LocalContext.current,
        fullName = "Levi Ackerman",
        email = "levi@gmail.com",
        photoProfile = "",
        navigateUp = {},
        snackbarHostState = SnackbarHostState(), body = { innerPadding ->
            ChatRoomBody(
                modifier = Modifier.padding(innerPadding),
                chats = listOf(
                    Chat(
                        id = "",
                        roomChatId = "",
                        senderId = "id1",
                        receiverId = "",
                        message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam sit amet risus auctor, volutpat dui et, luctus nulla.",
                        createdAt = 2999991029,
                    ),
                    Chat(
                        id = "",
                        roomChatId = "",
                        senderId = "id1",
                        receiverId = "",
                        message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam sit amet risus.",
                        createdAt = 2999991029,
                    ),
                    Chat(
                        id = "",
                        roomChatId = "",
                        senderId = "id1",
                        receiverId = "",
                        message = "Lorem ipsum dolor sit amet, consectetur adipiscing",
                        createdAt = 2999991029,
                    ),
                    Chat(
                        id = "",
                        roomChatId = "",
                        senderId = "id2",
                        receiverId = "",
                        message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                        createdAt = 2999991029,
                    ),
                    Chat(
                        id = "",
                        roomChatId = "",
                        senderId = "id1",
                        receiverId = "",
                        message = "Lorem ipsum dolor sit amet, consectetur adipiscing",
                        createdAt = 2999991029,
                    ),
                    Chat(
                        id = "",
                        roomChatId = "",
                        senderId = "id2",
                        receiverId = "",
                        message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                        createdAt = 2999991029,
                    ),
                    Chat(
                        id = "",
                        roomChatId = "",
                        senderId = "id1",
                        receiverId = "",
                        message = "Lorem",
                        createdAt = 2999991029,
                    ),
                    Chat(
                        id = "",
                        roomChatId = "",
                        senderId = "id1",
                        receiverId = "",
                        message = "Lorem ipsum dolor sit amet, consectetur adipiscing",
                        createdAt = 2999991029,
                    ),
                    Chat(
                        id = "",
                        roomChatId = "",
                        senderId = "id2",
                        receiverId = "",
                        message = "Lorem ipsum dolor sit amet, consectetur adipiscing",
                        createdAt = 2999991029,
                    ),
                    Chat(
                        id = "",
                        roomChatId = "",
                        senderId = "id1",
                        receiverId = "",
                        message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam sit amet risus auctor, volutpat dui et, luctus nulla.",
                        createdAt = 2999991029,
                    ),
                ),
                message = "",
                textFieldScrollState = rememberScrollState(),
                onMessageChanged = {},
                onSender = {
                    "id2" == it
                },
                onSentMessage = {}
            )
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatRoomContent(
    fullName: String,
    email: String,
    context: Context,
    photoProfile: String?,
    navigateUp: () -> Unit,
    snackbarHostState: SnackbarHostState,
    body: @Composable (PaddingValues) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = navigateUp) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    scrolledContainerColor = MaterialTheme.colorScheme.surface
                ),
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top
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
                                    .size(45.dp)
                            )
                        } ?: Box(
                            modifier = Modifier
                                .border(1.dp, Color.White, CircleShape)
                                .background(
                                    color = MaterialTheme.colorScheme.secondaryContainer,
                                    shape = CircleShape
                                )
                                .size(45.dp)
                        ) {
                            Text(
                                text = fullName[0].toString(),
                                style = MaterialTheme.typography.titleMedium,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = fullName,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(modifier = Modifier.height(1.dp))
                            Text(
                                text = email,
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }) { innerPadding ->
        body(innerPadding)
    }
}

@Preview(showBackground = true)
@Composable
fun BubbleMessageUserIsMePreview() {
    DicodingMentoringTheme {
        BubbleMessage(
            message = "Halo", isUserMe = false, time = "23:24"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BubbleMessageUserIsNotMePreview() {
    DicodingMentoringTheme {
        BubbleMessage(
            message = "Halo guys david disini",
            isUserMe = false,
            time = "23:28",
        )
    }
}

@Composable
fun ChatRoomBody(
    chats: List<Chat>,
    modifier: Modifier = Modifier,
    message: String,
    textFieldScrollState: ScrollState,
    onMessageChanged: (String) -> Unit,
    onSender: (String) -> Boolean,
    onSentMessage: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = modifier.align(Alignment.TopCenter),
            contentPadding = PaddingValues(horizontal = 16.dp),
            content = {
                items(chats) { chat ->
                    val isUserMe = onSender(chat.senderId)
                    if (isUserMe)
                        Spacer(modifier = Modifier.height(8.dp))
                    BubbleMessage(
                        message = chat.message,
                        time = chat.createdAt.withTimeFormat(),
                        isUserMe = isUserMe
                    )
                    Spacer(modifier = Modifier.height(if (isUserMe) 12.dp else 6.dp))
                }
            })
        OutlinedTextField(
            value = message,
            maxLines = 4,
            onValueChange = onMessageChanged,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 4.dp)
                .verticalScroll(textFieldScrollState),
            placeholder = {
                Text(
                    text = "Write message....",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                errorContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                focusedBorderColor = MaterialTheme.colorScheme.surfaceVariant,
                unfocusedBorderColor = MaterialTheme.colorScheme.surfaceVariant,
                disabledBorderColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
            shape = MaterialTheme.shapes.extraLarge,
            trailingIcon = {
                IconButton(onClick = onSentMessage) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "send message",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            })
    }
}

@Composable
fun BubbleMessage(
    message: String,
    time: String,
    isUserMe: Boolean,
    modifier: Modifier = Modifier,
) {
    val backgroundBubbleColor = if (isUserMe) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }
    val bubbleShape = if (isUserMe) RoundedCornerShape(4.dp, 20.dp, 4.dp, 20.dp)
    else RoundedCornerShape(20.dp, 4.dp, 20.dp, 4.dp)

    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val bubbleContainer = createRef()
        Box(modifier = modifier
            .fillMaxWidth(0.7f)
            .constrainAs(bubbleContainer) {
                if (isUserMe)
                    end.linkTo(parent.end)
                else start.linkTo(parent.start)
            }) {
            Surface(
                color = backgroundBubbleColor,
                shape = bubbleShape,
                tonalElevation = 2.dp,
                shadowElevation = 2.dp
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                ) {
                    ConstraintLayout {
                        val (userMessage, createdAt) = createRefs()
                        SelectionContainer(modifier = Modifier.constrainAs(userMessage) {
                            top.linkTo(parent.top)
                        }) {
                            Text(
                                textAlign = TextAlign.Start,
                                color = if (isUserMe) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                                text = message,
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = time,
                            textAlign = TextAlign.End,
                            color = if (isUserMe) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.constrainAs(createdAt) {
                                top.linkTo(userMessage.bottom)
                                end.linkTo(userMessage.end, margin = 2.dp)
                            }
                        )
                    }
                }
            }
        }
    }
}
