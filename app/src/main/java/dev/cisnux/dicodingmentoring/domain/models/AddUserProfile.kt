package dev.cisnux.dicodingmentoring.domain.models

import android.net.Uri

data class AddUserProfile(
    val id: String,
    val fullName: String,
    val username: String,
    val email: String,
    val job: String,
    val experienceLevel: String,
    val interests: List<String>,
    val photoProfileUri: Uri? = null,
    val motto: String? = null,
)
