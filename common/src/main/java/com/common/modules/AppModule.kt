package com.common.modules

import android.app.Application
import android.content.Context
import com.common.utils.PreferenceStorage
import com.common.utils.SharedPreferenceStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideContext(application: Application): Context = application.applicationContext

    @Singleton
    @Provides
    fun providesPreferenceStorage(context: Context): PreferenceStorage = SharedPreferenceStorage(context)
}