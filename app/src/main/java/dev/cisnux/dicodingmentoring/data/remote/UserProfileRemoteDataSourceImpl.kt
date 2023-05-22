package dev.cisnux.dicodingmentoring.data.remote

import android.util.Log
import dev.cisnux.dicodingmentoring.data.services.FileService
import dev.cisnux.dicodingmentoring.data.services.UserProfileService
import dev.cisnux.dicodingmentoring.data.services.asGetUserProfile
import dev.cisnux.dicodingmentoring.domain.models.AddUserProfile
import dev.cisnux.dicodingmentoring.domain.models.GetUserProfile
import dev.cisnux.dicodingmentoring.utils.Failure
import dev.cisnux.dicodingmentoring.utils.HTTP_FAILURES
import dev.cisnux.dicodingmentoring.utils.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UserProfileRemoteDataSourceImpl
@Inject constructor(private val service: UserProfileService, private val fileService: FileService) :
    UserProfileRemoteDataSource {
    override fun getUserProfileById(id: String): Flow<UiState<GetUserProfile>> = flow {
        try {
            emit(UiState.Loading)
            val response = service.getUserProfilesById(id)
            emit(UiState.Success(response.data?.asGetUserProfile()))
        } catch (e: IOException) {
            emit(UiState.Error(Failure.ConnectionFailure("No internet connection")))
        } catch (e: HttpException) {
            val statusCode = e.response()?.code()
            val failure = HTTP_FAILURES[statusCode]
            val errorBody = e.response()?.errorBody()?.string()
            errorBody?.let {
                failure?.message = JSONObject(it).getString("message")
            }
            emit(UiState.Error((failure ?: e)))
        } catch (e: Exception) {
            emit(UiState.Error(e))
        }
    }

    override fun postUserProfile(
        userProfile: AddUserProfile
    ): Flow<UiState<Nothing>> = flow {
        try {
            emit(UiState.Loading)
            val photoFile = userProfile.photoProfileUri?.let {
                val file = fileService.uriToFile(it)
                fileService.reduceImage(file)
            }
            val id = userProfile.id
            val fullName = userProfile.fullName.toRequestBody("text/plain".toMediaType())
            val username = userProfile.username.toRequestBody("text/plain".toMediaType())
            val email = userProfile.email.toRequestBody("text/plain".toMediaType())
            val job = userProfile.job.toRequestBody("text/plain".toMediaType())
            val interests = userProfile.interests.map {
                it.toRequestBody("text/plain".toMediaType())
            }
            val experienceLevel =
                userProfile.experienceLevel.toRequestBody("text/plain".toMediaType())
            val motto = userProfile.motto?.toRequestBody("text/plain".toMediaType())
            val requestImageFile = photoFile?.asRequestBody("image/jpg".toMediaType())
            val imageMultipart = requestImageFile?.let {
                MultipartBody.Part.createFormData(
                    "photo",
                    photoFile.name,
                    it
                )
            }
            service.postUserProfile(
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
            emit(UiState.Success())
        } catch (e: IOException) {
            emit(UiState.Error(Failure.ConnectionFailure("No internet connection")))
        } catch (e: HttpException) {
            val statusCode = e.response()?.code()
            val failure = HTTP_FAILURES[statusCode]
            val errorBody = e.response()?.errorBody()?.string()
            errorBody?.let {
                failure?.message = JSONObject(it).getString("message")
            }
            emit(UiState.Error((failure ?: e)))
        } catch (e: Exception) {
            emit(UiState.Error(e))
        }
    }


    override fun isUserProfileExist(id: String): Flow<UiState<Boolean>> = flow {
        try {
            emit(UiState.Loading)
            service.getUserProfilesById(id)
            emit(UiState.Success(true))
        } catch (e: IOException) {
            Log.d(UserProfileRemoteDataSourceImpl::class.simpleName, e.stackTraceToString())
            emit(UiState.Error(Failure.ConnectionFailure("No internet connection")))
        } catch (e: HttpException) {
            emit(UiState.Success(false))
        } catch (e: Exception) {
            emit(UiState.Error(Failure.ConnectionFailure("No internet connection")))
            emit(UiState.Success(false))
        }
    }

}