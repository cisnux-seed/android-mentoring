package dev.cisnux.dicodingmentoring.data.repositories

import android.util.Log
import arrow.core.Either
import dev.cisnux.dicodingmentoring.data.remote.UserProfileRemoteDataSource
import dev.cisnux.dicodingmentoring.data.services.FileService
import dev.cisnux.dicodingmentoring.data.services.asGetUserProfile
import dev.cisnux.dicodingmentoring.domain.models.AddUserProfile
import dev.cisnux.dicodingmentoring.domain.models.GetUserProfile
import dev.cisnux.dicodingmentoring.domain.repositories.UserProfileRepository
import dev.cisnux.dicodingmentoring.utils.Failure
import dev.cisnux.dicodingmentoring.utils.HTTP_FAILURES
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UserProfileRepositoryImpl @Inject constructor(
    private val remoteDataSource: UserProfileRemoteDataSource,
    private val fileService: FileService,
) :
    UserProfileRepository {

    override suspend fun getUserProfileById(id: String): Either<Exception, GetUserProfile> =
        withContext(Dispatchers.IO) {
            try {
                val userProfileResponse = remoteDataSource.getUserProfileById(id)
                Either.Right(userProfileResponse.data.asGetUserProfile())
            } catch (e: IOException) {
                Either.Left(Failure.ConnectionFailure("No internet connection"))
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

    override suspend fun postUserProfile(userProfile: AddUserProfile): Either<Exception, Nothing?> =
        withContext(Dispatchers.IO) {
            try {
                val photoFile = userProfile.photoProfileUri?.let {
                    val file = fileService.uriToFile(it)
                    fileService.reduceImage(file)
                }
                Log.d(
                    UserProfileRepositoryImpl::class.simpleName,
                    photoFile?.toString() ?: "No File"
                )
                val id = userProfile.id
                val fullName = userProfile.fullName.toRequestBody("text/plain".toMediaType())
                val username = userProfile.username.toRequestBody("text/plain".toMediaType())
                val email = userProfile.email.toRequestBody("text/plain".toMediaType())
                val job = userProfile.job.toRequestBody("text/plain".toMediaType())
                val interests =
                    userProfile.interests.joinToString(",")
                        .toRequestBody("text/plain".toMediaType())
                val experienceLevel =
                    userProfile.experienceLevel.toRequestBody("text/plain".toMediaType())
                val motto = userProfile.motto?.toRequestBody("text/plain".toMediaType())
                val requestImageFile = photoFile?.asRequestBody("image/jpg".toMediaType())
                val imageMultipart = requestImageFile?.let {
                    MultipartBody.Part.createFormData(
                        "photoProfile",
                        photoFile.name,
                        it
                    )
                }
                remoteDataSource.postUserProfile(
                    id,
                    imageMultipart,
                    fullName,
                    username,
                    email,
                    job,
                    interests,
                    experienceLevel,
                    motto,
                )
                Either.Right(null)
            } catch (e: IOException) {
                Either.Left(Failure.ConnectionFailure("No internet connection"))
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
}