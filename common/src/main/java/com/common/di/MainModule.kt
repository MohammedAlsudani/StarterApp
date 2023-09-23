package com.common.di

import com.common.repository.CommonRepository
import com.common.repository.CommonRepositoryImpl
import com.common.utils.SharedPreferenceStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Module class that provisions dependencies for the Main module.
 *
 */
@Module
@InstallIn(SingletonComponent::class)
class MainModule {

    @Provides
    fun provideMainRepository(sharedPreferenceStorage: SharedPreferenceStorage): CommonRepository {
        return CommonRepositoryImpl(sharedPreferenceStorage)
    }
}