package dev.cisnux.dicodingmentoring.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.cisnux.dicodingmentoring.data.remote.UserProfileRemoteDataSource
import dev.cisnux.dicodingmentoring.data.remote.UserProfileRemoteDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UserProfileRemoteDataSourceModule {

    @Singleton
    @Binds
    abstract fun bindUserProfileRemoteDataSource(
        userProfileRemoteDataSourceImpl: UserProfileRemoteDataSourceImpl
    ): UserProfileRemoteDataSource
}