package dev.cisnux.dicodingmentoring.data.remote

import dev.cisnux.dicodingmentoring.data.services.MentorResponse
import dev.cisnux.dicodingmentoring.data.services.MentorRequestBody

interface MentorRemoteDataSource {
    suspend fun getMentorProfileById(id: String): MentorResponse
    suspend fun addMentorProfile(id: String, mentor: MentorRequestBody)
}