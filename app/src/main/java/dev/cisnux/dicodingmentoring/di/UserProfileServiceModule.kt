package dev.cisnux.dicodingmentoring.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.cisnux.dicodingmentoring.data.services.UserProfileService
import dev.cisnux.dicodingmentoring.utils.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserProfileServiceModule {

    @Singleton
    @Provides
    fun provideUserProfileService(
        client: OkHttpClient,
        moshiConverter: MoshiConverterFactory,
    ): UserProfileService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(moshiConverter)
            .client(client)
            .build()
            .create(UserProfileService::class.java)
    }
}