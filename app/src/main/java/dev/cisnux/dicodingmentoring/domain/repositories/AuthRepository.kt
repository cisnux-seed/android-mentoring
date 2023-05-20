package dev.cisnux.dicodingmentoring.domain.repositories

import arrow.core.Either
import dev.cisnux.dicodingmentoring.domain.models.AuthUser
import dev.cisnux.dicodingmentoring.domain.models.AuthenticatedUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun register(user: AuthUser): Either<Exception, AuthenticatedUser?>
    suspend fun login(user: AuthUser): Either<Exception, AuthenticatedUser?>
    suspend fun resetPassword(email: String): Either<Exception, Nothing?>
    suspend fun signInWithGoogle(token: String?): Either<Exception, AuthenticatedUser?>
    suspend fun logout()
    suspend fun currentUser(): Flow<AuthenticatedUser?>
}