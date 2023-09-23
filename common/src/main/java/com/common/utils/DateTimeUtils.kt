package com.common.utils

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.PropertyName
import java.util.*
import kotlin.time.Duration.Companion.microseconds

object DateTimeUtils {
    /**
     * get current date
     * @return return Firebase current date .
     */
    @JvmStatic
    fun getCurrentTimeStampDate(): String {
        val model = FieldValueTimestampConverter().apply {
            this.timestamp to FieldValue.serverTimestamp()
        }
        return model.timestamp.time.toString().trim()
    }
}

data class FieldValueTimestampConverter(
    @get: PropertyName("timestamp") @set: PropertyName("timestamp") var timestamp: Date = Date()
)