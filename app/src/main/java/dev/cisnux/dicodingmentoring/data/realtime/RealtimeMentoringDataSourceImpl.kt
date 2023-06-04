package dev.cisnux.dicodingmentoring.data.realtime

import dev.cisnux.dicodingmentoring.utils.WS_BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.url
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject


class RealtimeMentoringDataSourceImpl @Inject constructor(
    private val client: HttpClient,
) : RealtimeMentoringDataSource {
    private var session: WebSocketSession? = null

    override fun getRealtimeMentoringSessions(userId: String): Flow<List<GetRealtimeMentoring>> =
        flow {
            session = client.webSocketSession {
                url("$WS_BASE_URL/mentoring?userId=$userId")
            }
            val mentoringSessions = session!!
                .incoming
                .consumeAsFlow()
                .filterIsInstance<Frame.Text>()
                .mapNotNull {
                    Json.Default.decodeFromString<List<GetRealtimeMentoring>>(it.readText())
                }
            emitAll(mentoringSessions)
        }


    override suspend fun createRealtimeMentoring(createRealtimeMentoring: CreateRealtimeMentoring) {
        session = client.webSocketSession {
            url("$WS_BASE_URL/mentoring")
        }

        session?.send(
            Frame.Text(Json.Default.encodeToString(createRealtimeMentoring))
        )
    }

    override suspend fun close(): Unit = withContext(Dispatchers.IO) {
        session?.close()
        session = null
    }
}