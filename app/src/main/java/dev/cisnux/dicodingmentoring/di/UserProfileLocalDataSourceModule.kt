package dev.cisnux.dicodingmentoring.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.cisnux.dicodingmentoring.data.local.AuthLocalDataSource
import dev.cisnux.dicodingmentoring.data.local.AuthLocalDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UserProfileLocalDataSourceModule {

    @Singleton
    @Binds
    abstract fun bindUserProfileLocalDataSource(
        userProfileLocalDataSourceImpl: AuthLocalDataSourceImpl
    ): AuthLocalDataSource
}