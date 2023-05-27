package dev.cisnux.dicodingmentoring.data.services

import com.squareup.moshi.Json
import dev.cisnux.dicodingmentoring.domain.models.GetUserProfile

data class UserProfileResponse(

    @Json(name = "data")
    val data: UserProfileData
)

data class UserProfileData(
    @Json(name = "id")
    val id: String,

    @Json(name = "fullName")
    val fullName: String,

    @Json(name = "isMentor")
    val isMentor: Boolean,

    @Json(name = "job")
    val job: String,

    @Json(name = "email")
    val email: String,

    @Json(name = "interests")
    val interests: List<String>,

    @Json(name = "username")
    val username: String,

    @Json(name = "experienceLevel")
    val experienceLevel: String,

    @Json(name = "photoProfileUrl")
    val photoProfileUrl: String? = null,

    @Json(name = "about")
    val about: String,
)

fun UserProfileData.asGetUserProfile() = GetUserProfile(
    id = id,
    fullName = fullName,
    username = username,
    email = email,
    job = job,
    experienceLevel = experienceLevel,
    interests = interests,
    isMentor = isMentor,
    about = about,
    photoProfileUrl = photoProfileUrl,
)
