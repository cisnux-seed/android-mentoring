package dev.cisnux.dicodingmentoring.ui.chatroom

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.cisnux.dicodingmentoring.data.realtime.AddChat
import dev.cisnux.dicodingmentoring.data.realtime.RealtimeChats
import dev.cisnux.dicodingmentoring.domain.repositories.AuthRepository
import dev.cisnux.dicodingmentoring.domain.repositories.ChatRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatRoomViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val authRepository: AuthRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val roomChatId = checkNotNull(savedStateHandle["roomChatId"]) as String
    private val _showConnectionError = mutableStateOf(false)
    val showConnectionError: State<Boolean> get() = _showConnectionError
    private val _realtimeChats = mutableStateOf<RealtimeChats?>(null)
    val realtimeChats: State<RealtimeChats?> get() = _realtimeChats
    private val _message = mutableStateOf("")
    val message: State<String> get() = _message
    private var _senderId = mutableStateOf<String?>(null)
    val currentUserId: State<String?> get() = _senderId

    init {
        viewModelScope.launch {
            authRepository.currentUser().collectLatest {
                if (it.uid.isNotBlank()) {
                    _senderId.value = it.uid
                    chatRepository.getRealtimeChats(it.uid, roomChatId)
                        .catch { error ->
                            _showConnectionError.value = true
                            Log.d("realtime error", error.message.toString())
                            Log.d("realtime error trace", error.stackTraceToString())
                        }
                        .collectLatest { chat ->
                            _realtimeChats.value = chat
                        }
                }
            }
        }
    }

    fun onMessageChanged(message: String) {
        _message.value = message
    }

    fun onSentNewMessage() = viewModelScope.launch {
        if (_senderId.value != null && _realtimeChats.value != null) {
            val addChat = AddChat(
                roomChatId = roomChatId,
                senderId = _senderId.value!!,
                receiverId = if (_realtimeChats.value!!.menteeId == currentUserId.value) _realtimeChats.value!!.mentorId
                else _realtimeChats.value!!.menteeId,
                message = _message.value
            )
            chatRepository.sentMessage(addChat)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch {
            chatRepository.onCloseSocket()
        }
    }
}