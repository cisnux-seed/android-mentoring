package dev.cisnux.dicodingmentoring.data.repositories

import android.content.Intent
import arrow.core.Either
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.GoogleAuthProvider
import dev.cisnux.dicodingmentoring.AuthenticatedUser
import dev.cisnux.dicodingmentoring.data.local.AuthLocalDataSource
import dev.cisnux.dicodingmentoring.data.remote.MenteeRemoteDataSource
import dev.cisnux.dicodingmentoring.domain.models.AuthUser
import dev.cisnux.dicodingmentoring.domain.repositories.AuthRepository
import dev.cisnux.dicodingmentoring.utils.Failure
import dev.cisnux.dicodingmentoring.utils.HTTP_FAILURES
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val googleClient: GoogleSignInClient,
    private val authLocalDataSource: AuthLocalDataSource,
    private val menteeRemoteDataSource: MenteeRemoteDataSource
) :
    AuthRepository {
    override suspend fun register(user: AuthUser): Either<Exception, String?> =
        withContext(Dispatchers.IO) {
            try {
                val (email, password) = user
                val authResult =
                    firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                val authUser = authResult.user
                authUser?.let {
                    it.email?.let { email ->
                        authLocalDataSource.saveAuthenticatedUser(
                            authUser.uid,
                            email
                        )
                    }
                }
                delay(500L)
                Either.Right(authUser?.uid)
            } catch (e: FirebaseAuthWeakPasswordException) {
                Either.Left(e)
            } catch (e: FirebaseAuthUserCollisionException) {
                Either.Left(e)
            } catch (e: IOException) {
                Either.Left(Failure.ConnectionFailure("no internet connection"))
            } catch (e: Exception) {
                Either.Left(e)
            }
        }

    override suspend fun signInWithEmailAndPassword(user: AuthUser): Either<Exception, String?> =
        withContext(Dispatchers.IO) {
            try {
                val (email, password) = user
                val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                val authUser = authResult.user
                authUser?.let {
                    it.email?.let { email ->
                        authLocalDataSource.saveAuthenticatedUser(
                            authUser.uid,
                            email
                        )
                    }
                }
                delay(500L)
                authUser?.uid?.let {
                    menteeRemoteDataSource.getMenteeProfileById(it)
                }
                Either.Right(authUser?.uid)
            } catch (e: FirebaseAuthInvalidUserException) {
                Either.Left(e)
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                Either.Left(e)
            } catch (e: HttpException) {
                val statusCode = e.response()?.code()
                val failure = HTTP_FAILURES[statusCode]
                val errorBody = e.response()?.errorBody()?.string()
                errorBody?.let {
                    failure?.message = JSONObject(it).getString("message")
                }
                Either.Left(failure ?: e)
            } catch (e: IOException) {
                Either.Left(Failure.ConnectionFailure("no internet connection"))
            } catch (e: Exception) {
                Either.Left(e)
            }
        }


    override suspend fun resetPassword(email: String): Either<Exception, Nothing?> =
        withContext(Dispatchers.IO) {
            try {
                firebaseAuth.sendPasswordResetEmail(email).await()
                Either.Right(null)
            } catch (e: FirebaseAuthInvalidUserException) {
                Either.Left(e)
            } catch (e: IOException) {
                Either.Left(Failure.ConnectionFailure("no internet connection"))
            } catch (e: Exception) {
                Either.Left(e)
            }
        }

    override suspend fun signInWithGoogle(token: String?): Either<Exception, String?> =
        withContext(Dispatchers.IO) {
            try {
                val credential = GoogleAuthProvider.getCredential(token, null)
                val authResult = firebaseAuth.signInWithCredential(credential).await()
                val authUser = authResult.user
                authUser?.let {
                    it.email?.let { email ->
                        authLocalDataSource.saveAuthenticatedUser(
                            authUser.uid,
                            email
                        )
                    }
                }
                delay(500L)
                authUser?.uid?.let {
                    menteeRemoteDataSource.getMenteeProfileById(it)
                }
                Either.Right(authUser?.uid)
            } catch (e: FirebaseAuthInvalidUserException) {
                Either.Left(e)
            } catch (e: FirebaseAuthUserCollisionException) {
                Either.Left(e)
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                Either.Left(e)
            } catch (e: IOException) {
                Either.Left(Failure.ConnectionFailure("no internet connection"))
            } catch (e: HttpException) {
                val statusCode = e.response()?.code()
                val failure = HTTP_FAILURES[statusCode]
                val errorBody = e.response()?.errorBody()?.string()
                errorBody?.let {
                    failure?.message = JSONObject(it).getString("message")
                }
                Either.Left(failure ?: e)
            } catch (e: Exception) {
                Either.Left(e)
            }
        }

    override fun getAuthSession(id: String): Flow<Boolean> = authLocalDataSource.getAuthSession(id)

    override suspend fun saveAuthSession(id: String, session: Boolean) =
        authLocalDataSource.saveAuthSession(id, session)

    override fun getGoogleIntent(): Intent = googleClient.signInIntent

    override suspend fun logout() {
        googleClient.signOut()
        firebaseAuth.signOut()
    }

    override suspend fun logout(id: String) {
        googleClient.signOut()
        authLocalDataSource.deleteAuthenticatedUser()
        authLocalDataSource.deleteSession(id)
        firebaseAuth.signOut()
    }

    override fun currentUser(): Flow<AuthenticatedUser> =
        authLocalDataSource.authenticatedUser

    companion object {
        val TAG = AuthRepositoryImpl::class.simpleName
    }
}