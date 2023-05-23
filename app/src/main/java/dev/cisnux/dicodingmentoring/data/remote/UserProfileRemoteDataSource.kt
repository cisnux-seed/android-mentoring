package dev.cisnux.dicodingmentoring.data.remote

import dev.cisnux.dicodingmentoring.data.services.UserProfileResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface UserProfileRemoteDataSource {
    suspend fun getUserProfileById(id: String): UserProfileResponse
    suspend fun postUserProfile(
        id: String,
        photoProfile: MultipartBody.Part?,
        fullName: RequestBody,
        username: RequestBody,
        email: RequestBody,
        job: RequestBody,
        interests: RequestBody,
        experienceLevel: RequestBody,
        motto: RequestBody?,
    )
}