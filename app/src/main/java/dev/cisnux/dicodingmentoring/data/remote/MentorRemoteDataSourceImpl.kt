package dev.cisnux.dicodingmentoring.data.remote

import dev.cisnux.dicodingmentoring.data.services.MentorResponse
import dev.cisnux.dicodingmentoring.data.services.MentorService
import dev.cisnux.dicodingmentoring.data.services.MentorRequestBody
import javax.inject.Inject

class MentorRemoteDataSourceImpl @Inject constructor(
    private val service: MentorService
) : MentorRemoteDataSource {
    override suspend fun getMentorProfileById(id: String): MentorResponse =
        service.getMentorProfileById(id)

    override suspend fun addMentorProfile(id: String, mentor: MentorRequestBody): Unit =
        service.postMentorProfile(id, mentor)
}