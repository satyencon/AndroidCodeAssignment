package com.llyods.assignment.di

import com.llyods.assignment.BuildConfig
import com.llyods.assignment.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        var retrofit: Retrofit? = null
            retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(
                    OkHttpClient().newBuilder()
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .build()
                )
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        return retrofit
    }

    @Singleton
    @Provides
    fun provideAppServices(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}