package dev.cisnux.dicodingmentoring.domain.repositories

import arrow.core.Either
import dev.cisnux.dicodingmentoring.domain.models.AddUserProfile
import dev.cisnux.dicodingmentoring.domain.models.GetUserProfile

interface UserProfileRepository {
    suspend fun getUserProfileById(id: String): Either<Exception, GetUserProfile>
    suspend fun postUserProfile(
        userProfile: AddUserProfile
    ): Either<Exception, Nothing?>
}