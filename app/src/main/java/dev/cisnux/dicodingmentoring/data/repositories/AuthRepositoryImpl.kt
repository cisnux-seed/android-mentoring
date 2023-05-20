package dev.cisnux.dicodingmentoring.data.repositories

import arrow.core.Either
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.GoogleAuthProvider
import dev.cisnux.dicodingmentoring.domain.models.AuthUser
import dev.cisnux.dicodingmentoring.domain.models.AuthenticatedUser
import dev.cisnux.dicodingmentoring.domain.repositories.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val firebaseAuth: FirebaseAuth) :
    AuthRepository {

    override suspend fun register(user: AuthUser): Either<Exception, AuthenticatedUser?> =
        withContext(Dispatchers.IO) {
            try {
                val (email, password) = user
                val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                Either.Right(authResult.user?.let {
                    AuthenticatedUser(it.uid, it.email)
                })
            } catch (e: FirebaseAuthWeakPasswordException) {
                Either.Left(e)
            } catch (e: FirebaseAuthUserCollisionException) {
                Either.Left(e)
            } catch (e: Exception) {
                Either.Left(e)
            }
        }

    override suspend fun login(user: AuthUser): Either<Exception, AuthenticatedUser?> =
        withContext(Dispatchers.IO) {
            try {
                val (email, password) = user
                val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                Either.Right(authResult.user?.let {
                    AuthenticatedUser(it.uid, it.email)
                })
            } catch (e: FirebaseAuthInvalidUserException) {
                Either.Left(e)
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                Either.Left(e)
            } catch (e: Exception) {
                Either.Left(e)
            }
        }


    override suspend fun resetPassword(email: String): Either<Exception, Nothing?> =
        withContext(Dispatchers.IO) {
            try {
                firebaseAuth.sendPasswordResetEmail(email)
                Either.Right(null)
            } catch (e: FirebaseAuthInvalidUserException) {
                Either.Left(e)
            } catch (e: Exception) {
                Either.Left(e)
            }
        }

    override suspend fun signInWithGoogle(token: String?): Either<Exception, AuthenticatedUser?> =
        withContext(Dispatchers.IO) {
            try {
                val credential = GoogleAuthProvider.getCredential(token, null)
                val authResult = firebaseAuth.signInWithCredential(credential).await()
                Either.Right(authResult.user?.let {
                    AuthenticatedUser(it.uid, it.email)
                })
            } catch (e: FirebaseAuthInvalidUserException) {
                firebaseAuth.signOut()
                Either.Left(e)
            } catch (e: FirebaseAuthUserCollisionException) {
                firebaseAuth.signOut()
                Either.Left(e)
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                firebaseAuth.signOut()
                Either.Left(e)
            } catch (e: Exception) {
                firebaseAuth.signOut()
                Either.Left(e)
            }
        }


    override suspend fun logout() = withContext(Dispatchers.IO) {
        firebaseAuth.signOut()
    }

    override suspend fun currentUser(): Flow<AuthenticatedUser?> = flow {
        emit(firebaseAuth.currentUser?.let {
            AuthenticatedUser(it.uid, it.email)
        })
    }

    companion object {
        val TAG = AuthRepositoryImpl::class.simpleName
    }
}