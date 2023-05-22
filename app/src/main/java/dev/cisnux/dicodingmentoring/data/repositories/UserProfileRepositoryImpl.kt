package dev.cisnux.dicodingmentoring.data.repositories

import dev.cisnux.dicodingmentoring.data.remote.UserProfileRemoteDataSource
import dev.cisnux.dicodingmentoring.domain.models.AddUserProfile
import dev.cisnux.dicodingmentoring.domain.models.GetUserProfile
import dev.cisnux.dicodingmentoring.domain.repositories.UserProfileRepository
import dev.cisnux.dicodingmentoring.utils.UiState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserProfileRepositoryImpl @Inject constructor(private val remoteDataSource: UserProfileRemoteDataSource) :
    UserProfileRepository {

    override fun getUserProfileById(id: String): Flow<UiState<GetUserProfile>> =
        remoteDataSource.getUserProfileById(id)

    override fun postUserProfile(userProfile: AddUserProfile): Flow<UiState<Nothing>> =
        remoteDataSource.postUserProfile(userProfile)

    override fun isUserProfileExist(id: String): Flow<UiState<Boolean>> =
        remoteDataSource.isUserProfileExist(id)
}