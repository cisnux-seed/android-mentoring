package dev.cisnux.dicodingmentoring.domain.models

data class AuthenticatedUser(
    val uid: String,
    val email: String?,
)