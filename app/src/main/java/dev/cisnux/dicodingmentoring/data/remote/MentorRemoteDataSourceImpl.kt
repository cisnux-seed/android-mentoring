package dev.cisnux.dicodingmentoring.data.remote

import dev.cisnux.dicodingmentoring.data.services.MentorDetailResponse
import dev.cisnux.dicodingmentoring.data.services.MentorListResponse
import dev.cisnux.dicodingmentoring.data.services.MentorRequestBody
import dev.cisnux.dicodingmentoring.data.services.MentorService
import javax.inject.Inject

class MentorRemoteDataSourceImpl @Inject constructor(
    private val service: MentorService
) : MentorRemoteDataSource {
    override suspend fun getMentorProfileById(id: String): MentorDetailResponse =
        service.getMentorProfileById(id)

    override suspend fun addMentorProfile(id: String, mentor: MentorRequestBody): Unit =
        service.postMentorProfile(id, mentor)

    override suspend fun getMentors(
        id: String,
        keyword: String?
    ): MentorListResponse =
        service.getMentors(id, keyword)

}