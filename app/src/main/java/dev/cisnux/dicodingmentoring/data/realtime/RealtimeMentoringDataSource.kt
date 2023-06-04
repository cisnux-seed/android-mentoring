package dev.cisnux.dicodingmentoring.data.realtime

import kotlinx.coroutines.flow.Flow

interface RealtimeMentoringDataSource {
    fun getRealtimeMentoringSessions(userId: String): Flow<List<GetRealtimeMentoring>>
    suspend fun createRealtimeMentoring(createRealtimeMentoring: CreateRealtimeMentoring)
    suspend fun close()
}