package dev.cisnux.dicodingmentoring.domain.repositories

import dev.cisnux.dicodingmentoring.domain.models.AddMentoringSession
import dev.cisnux.dicodingmentoring.domain.models.GetMentoringSession
import kotlinx.coroutines.flow.Flow

interface MentoringRepository {
    fun getMentoringSessions(userId: String): Flow<List<GetMentoringSession>>
    suspend fun createMentoring(addMentoringSession: AddMentoringSession)
    suspend fun closeSocketSessions()
}