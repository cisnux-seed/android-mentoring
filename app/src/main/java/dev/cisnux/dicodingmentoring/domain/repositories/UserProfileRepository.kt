package dev.cisnux.dicodingmentoring.domain.repositories

import dev.cisnux.dicodingmentoring.domain.models.AddUserProfile
import dev.cisnux.dicodingmentoring.domain.models.GetUserProfile
import dev.cisnux.dicodingmentoring.utils.UiState
import kotlinx.coroutines.flow.Flow

interface UserProfileRepository {
    fun getUserProfileById(id: String): Flow<UiState<GetUserProfile>>
    fun postUserProfile(
        userProfile: AddUserProfile
    ): Flow<UiState<Nothing>>
    fun isUserProfileExist(id: String): Flow<UiState<Boolean>>
}