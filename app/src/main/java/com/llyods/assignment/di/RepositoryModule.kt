package com.llyods.assignment.di

import com.llyods.assignment.domain.mapper.UserDomainMapper
import com.llyods.assignment.data.remote.ApiService
import com.llyods.assignment.data.repository.AppRepositoryImpl
import com.llyods.assignment.domain.repository.AppRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.DelicateCoroutinesApi
import javax.inject.Singleton

@DelicateCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideRepository(apiService: ApiService, mapper: UserDomainMapper): AppRepository {
        return AppRepositoryImpl(apiService, mapper)
    }
}