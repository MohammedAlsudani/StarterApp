package com.common.database.converters

import androidx.room.TypeConverter
import com.common.database.entities.Answer
import com.google.common.reflect.TypeToken
import com.google.firebase.firestore.GeoPoint
import com.google.gson.Gson
import java.util.Date

/**
 * Class responsible for converting the data type for room database.
 *
 */
class DateConverter {

    private val gson = Gson()

    @TypeConverter
    fun timestampToDate(value: Long): Date = Date(value)

    @TypeConverter
    fun dateToTimestamp(date: Date): Long = date.time

    @TypeConverter
    fun fromString(value: String?): List<String>? =
        value?.let { gson.fromJson(it, object : TypeToken<List<String>>() {}.type) }

    @TypeConverter
    fun toString(list: List<String>?): String? = list?.let { gson.toJson(it) }

    @TypeConverter
    fun toGeoPoint(value: String?): GeoPoint? =
        value?.let { gson.fromJson(it, GeoPoint::class.java) }

    @TypeConverter
    fun fromGeoPoint(geoPoint: GeoPoint?): String? = geoPoint?.let { gson.toJson(it) }

    @TypeConverter
    fun fromMap(value: String?): Map<String, String>? =
        value?.let { gson.fromJson(it, object : TypeToken<Map<String, String>>() {}.type) }

    @TypeConverter
    fun toMap(map: Map<String, String>?): String? = map?.let { gson.toJson(it) }


    @TypeConverter
    fun fromJson(json: String): List<Answer> {
        val type = object : TypeToken<List<Answer>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJson(list: List<Answer>): String {
        return Gson().toJson(list)
    }
}
