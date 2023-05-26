package dev.cisnux.dicodingmentoring.ui.chatting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.cisnux.dicodingmentoring.ui.theme.DicodingMentoringTheme

@Composable
fun ChattingScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        TitleChatSection(
            name = "Thoriq Hidayat",
            isOnline = true
        )
        
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        ) {
            items(messages_dummy) { message ->
                ChattingItem(message)
                Spacer(modifier = Modifier.height(4.dp))
            }
        }

        ChattingInput()
    }
}

@Composable
fun TitleChatSection(name: String, isOnline: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(MaterialTheme.colorScheme.primary),
    ) {
        Row(modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = name,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )
                Text(text = if (isOnline) "Online now" else "Offline",
                    fontSize = 12.sp,
                    color = if (isOnline) Color(0xFF34A853) else Color.Black
                )
            }
        }
    }
}

@Composable
fun ChattingItem(
    message: Message,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier.fillMaxWidth(),
        horizontalAlignment = if (message.isMine) Alignment.End else Alignment.Start
    ) {
        Card(
            modifier = Modifier
                .padding(8.dp),
            colors = CardDefaults.cardColors(
                containerColor =
                if (message.isMine) MaterialTheme.colorScheme.primary else
                    Color.LightGray
            ),
            shape = chattingRounded(message),
        ) {
            Text(text = message.message,
                color = when {
                    message.isMine -> Color.White
                    else -> Color.Black },
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChattingInput() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = "",
            placeholder = { Text("Type Message")},
            onValueChange = {},
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        )
        Button(
            onClick = {},
            modifier = Modifier
                .padding(end = 8.dp)
                .height(50.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "send",
            )
        }
    }
}


@Composable
fun chattingRounded(message: Message): Shape {
    val roundedCorners = RoundedCornerShape(16.dp)
    return when {
        message.isMine -> roundedCorners.copy(bottomEnd = CornerSize(0))
        else -> roundedCorners.copy(topStart = CornerSize(0))
    }
}

@Preview(showBackground = true)
@Composable
fun ChattingPreview(){
    DicodingMentoringTheme {
        ChattingScreen()
    }
}


data class Message(
    val message: String,
    val isMine: Boolean
)

val messages_dummy = listOf(
    Message( "Haloo bang", true),
    Message( "Haloo juga bang", false),
    Message("tes aja bang", true),
    Message("okee bang", false),
)



