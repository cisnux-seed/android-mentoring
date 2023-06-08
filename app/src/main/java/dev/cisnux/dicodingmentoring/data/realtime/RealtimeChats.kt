package dev.cisnux.dicodingmentoring.data.realtime

import kotlinx.serialization.Serializable

@Serializable
data class RealtimeChats(
    val roomChatId: String,
    val mentorId: String,
    val menteeId: String,
    val endOfChatting: Long,
    val chats: List<Chat>
)

@Serializable
data class Chat(
    val id: String,
    val roomChatId: String,
    val senderId: String,
    val receiverId: String,
    val message: String,
    val createdAt: Long = System.currentTimeMillis()
)