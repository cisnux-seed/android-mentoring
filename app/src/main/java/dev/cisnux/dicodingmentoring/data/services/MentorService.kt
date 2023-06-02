package dev.cisnux.dicodingmentoring.data.services

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MentorService {
    @POST("mentors/{id}")
    suspend fun postMentorProfile(
        @Path("id") id: String,
        @Body mentorRequestBody: MentorRequestBody
    )

    @GET("mentors/{id}")
    suspend fun getMentorProfileById(
        @Path("id") id: String,
    ): MentorDetailResponse


    @GET("mentors/list/{id}")
    suspend fun getMentors(
        @Path("id") id: String,
        @Query("keyword") keyword: String?,
    ): MentorListResponse
}