package dev.cisnux.dicodingmentoring.data.services

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface UserProfileService {
    @Multipart
    @POST("userProfiles/{id}")
    suspend fun postUserProfile(
        @Path("id") id: String,
        @Part photoProfile: MultipartBody.Part?,
        @Part("fullName") fullName: RequestBody,
        @Part("username") username: RequestBody,
        @Part("email") email: RequestBody,
        @Part("job") job: RequestBody,
        @Part("interests[]") interests: List<RequestBody>,
        @Part("experienceLevel") experienceLevel: RequestBody,
        @Part("motto") motto: RequestBody?,
    )

    @GET("userProfiles/{id}")
    suspend fun getUserProfilesById(
        @Path("id") id: String,
    ): UserProfileResponse
}