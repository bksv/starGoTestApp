package com.example.stargotestapp.di

import com.example.stargotestapp.model.api.PeopleApi
import com.example.stargotestapp.model.repositories.PeopleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providePeopleRepository(api : PeopleApi): PeopleRepository = PeopleRepository(api)
}