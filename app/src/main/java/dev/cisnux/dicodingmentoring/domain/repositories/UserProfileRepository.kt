package dev.cisnux.dicodingmentoring.domain.repositories

import arrow.core.Either
import dev.cisnux.dicodingmentoring.domain.models.AddMentor
import dev.cisnux.dicodingmentoring.domain.models.AddUserProfile
import dev.cisnux.dicodingmentoring.domain.models.Expertise
import dev.cisnux.dicodingmentoring.domain.models.GetUserProfile

interface UserProfileRepository {
    suspend fun getUserProfileById(id: String): Either<Exception, GetUserProfile>
    suspend fun addMenteeProfile(
        userProfile: AddUserProfile
    ): Either<Exception, Nothing?>

    suspend fun addMentorProfile(
        addMentor: AddMentor
    ): Either<Exception, Nothing?>
}