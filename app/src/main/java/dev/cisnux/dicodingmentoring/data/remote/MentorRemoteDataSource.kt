package dev.cisnux.dicodingmentoring.data.remote

import dev.cisnux.dicodingmentoring.data.services.MentorDetailResponse
import dev.cisnux.dicodingmentoring.data.services.MentorListResponse
import dev.cisnux.dicodingmentoring.data.services.MentorRequestBody

interface MentorRemoteDataSource {
    suspend fun getMentorProfileById(id: String): MentorDetailResponse
    suspend fun addMentorProfile(id: String, mentor: MentorRequestBody)
    suspend fun getMentors(
        id: String,
        keyword: String? = null
    ): MentorListResponse
}