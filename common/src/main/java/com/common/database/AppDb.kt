package com.common.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.common.BuildConfig
import com.common.database.converters.DateConverter
import com.common.database.entities.User

@Database(entities = [User::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AppDb : RoomDatabase() {


    companion object {
        const val DATABASE_NAME = BuildConfig.DATABASE_NAME
    }
}
