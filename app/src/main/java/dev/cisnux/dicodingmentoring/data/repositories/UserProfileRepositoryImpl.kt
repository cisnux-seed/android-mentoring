package dev.cisnux.dicodingmentoring.data.repositories

import arrow.core.Either
import dev.cisnux.dicodingmentoring.data.remote.MenteeRemoteDataSource
import dev.cisnux.dicodingmentoring.data.remote.MentorRemoteDataSource
import dev.cisnux.dicodingmentoring.data.services.ExpertisesItem
import dev.cisnux.dicodingmentoring.data.services.FileService
import dev.cisnux.dicodingmentoring.data.services.MentorRequestBody
import dev.cisnux.dicodingmentoring.data.services.asExpertises
import dev.cisnux.dicodingmentoring.domain.models.AddMentor
import dev.cisnux.dicodingmentoring.domain.models.AddUserProfile
import dev.cisnux.dicodingmentoring.domain.models.GetUserProfile
import dev.cisnux.dicodingmentoring.domain.models.asExpertiseItems
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
    private val menteeRemoteDataSource: MenteeRemoteDataSource,
    private val fileService: FileService,
    private val mentorRemoteDataSource: MentorRemoteDataSource
) : UserProfileRepository {

    override suspend fun getUserProfileById(id: String): Either<Exception, GetUserProfile> =
        withContext(Dispatchers.IO) {
            try {
                val userProfileResponse = menteeRemoteDataSource.getMenteeProfileById(id).data
                val expertises: List<ExpertisesItem> =
                    if (userProfileResponse.isMentorValid)
                        mentorRemoteDataSource.getMentorProfileById(id).data.expertises
                    else emptyList()
                val getUserProfile = GetUserProfile(
                    id = userProfileResponse.id,
                    photoProfileUrl = userProfileResponse.photoProfileUrl,
                    fullName = userProfileResponse.fullName,
                    username = userProfileResponse.username,
                    email = userProfileResponse.email,
                    job = userProfileResponse.job,
                    about = userProfileResponse.about,
                    isMentorValid = userProfileResponse.isMentorValid,
                    expertises = expertises.asExpertises()
                )
                Either.Right(getUserProfile)
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

    override suspend fun addMenteeProfile(userProfile: AddUserProfile): Either<Exception, Nothing?> =
        withContext(Dispatchers.IO) {
            try {
                val photoFile = userProfile.photoProfileUri?.let {
                    val file = fileService.uriToFile(it)
                    fileService.reduceImage(file)
                }
                val id = userProfile.id
                val fullName = userProfile.fullName.toRequestBody("text/plain".toMediaType())
                val username = userProfile.username.toRequestBody("text/plain".toMediaType())
                val email = userProfile.email.toRequestBody("text/plain".toMediaType())
                val job = userProfile.job.toRequestBody("text/plain".toMediaType())
                val about = userProfile.about.toRequestBody("text/plain".toMediaType())
                val requestImageFile = photoFile?.asRequestBody("image/jpg".toMediaType())
                val imageMultipart = requestImageFile?.let {
                    MultipartBody.Part.createFormData(
                        "photoProfile",
                        photoFile.name,
                        it
                    )
                }
                menteeRemoteDataSource.postMenteeProfile(
                    id,
                    imageMultipart,
                    fullName,
                    username,
                    email,
                    job,
                    about,
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

    override suspend fun addMentorProfile(
        addMentor: AddMentor
    ): Either<Exception, Nothing?> =
        withContext(Dispatchers.IO) {
            try {
                val expertiseItems = addMentor.expertises.asExpertiseItems()
                val mentorRequestBody = MentorRequestBody(
                    expertises = expertiseItems
                )
                mentorRemoteDataSource.addMentorProfile(addMentor.id, mentorRequestBody)
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