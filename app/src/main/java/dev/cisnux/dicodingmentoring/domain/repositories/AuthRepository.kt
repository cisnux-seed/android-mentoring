package dev.cisnux.dicodingmentoring.domain.repositories

import android.content.Intent
import arrow.core.Either
import dev.cisnux.dicodingmentoring.domain.models.AuthUser
import dev.cisnux.dicodingmentoring.domain.models.AuthenticatedUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun register(user: AuthUser): Either<Exception, AuthenticatedUser?>
    suspend fun signInWithEmailAndPassword(user: AuthUser): Either<Exception, Nothing?>
    suspend fun resetPassword(email: String): Either<Exception, Nothing?>
    suspend fun signInWithGoogle(token: String?): Either<Exception, Nothing?>
    fun getAuthSession(id: String): Flow<Boolean>
    suspend fun saveAuthSession(id: String, session: Boolean)
    fun getGoogleIntent(): Intent
    fun logout()
    fun currentUser(): AuthenticatedUser?
}