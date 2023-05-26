package dev.cisnux.dicodingmentoring.data.remote

import dev.cisnux.dicodingmentoring.data.services.UserProfileResponse
import dev.cisnux.dicodingmentoring.data.services.UserProfileService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class UserProfileRemoteDataSourceImpl
@Inject constructor(private val service: UserProfileService) :
    UserProfileRemoteDataSource {
    override suspend fun getUserProfileById(id: String): UserProfileResponse =
        service.getUserProfilesById(id)

    override suspend fun postUserProfile(
        id: String,
        photoProfile: MultipartBody.Part?,
        fullName: RequestBody,
        username: RequestBody,
        email: RequestBody,
        job: RequestBody,
        interests: RequestBody,
        experienceLevel: RequestBody,
        about: RequestBody
    ) = service.postUserProfile(
        id,
        photoProfile,
        fullName,
        username,
        email,
        job,
        interests,
        experienceLevel,
        about
    )

}