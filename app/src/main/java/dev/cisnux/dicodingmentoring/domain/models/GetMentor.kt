package dev.cisnux.dicodingmentoring.domain.models


data class GetMentor(
    val id: String,
    val photoProfileUrl: String?,
    val averageRating: Double,
    val fullName: String,
    val job: String
)
