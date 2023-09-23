package com.common.modules

import android.content.Context
import androidx.room.Room
import com.common.database.AppDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Module that provides the [AppDb] dependencies
 *
 */
@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDb::class.java, AppDb.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

//    /**
//     * This allows one to retrieve data from and persist data to the User Quiz
//     * from anywhere in the app.
//     */
//    @Singleton
//    @Provides
//    fun provideQuizDao(database: AppDb) = database.quizDao()
}
