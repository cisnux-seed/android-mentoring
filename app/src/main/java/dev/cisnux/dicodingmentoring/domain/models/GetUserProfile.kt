package dev.cisnux.dicodingmentoring.domain.models

data class GetUserProfile(
    val id: String,
    val fullName: String,
    val username: String,
    val email: String,
    val job: String,
    val experienceLevel: String,
    val interests: List<String>,
    val about: String,
    val photoProfileUrl: String? = null,
    val skills: String? = null,
    val rating: Double? = null,
    val expertises: String? = null,
)
