package com.common.utils

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.util.Log
import com.common.BuildConfig
import com.common.R
import com.google.android.material.datepicker.CalendarConstraints
import com.google.firebase.crashlytics.FirebaseCrashlytics
import java.io.BufferedReader
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone


class AppUtil {

    companion object {
        /**
         * Logs the Exception
         *
         * @param tagName     Tag for the exception
         * @param priority    Priority level
         * @param description Description
         */
        @JvmStatic
        fun logException(tagName: String?, priority: Int, description: String?) {
            val tagName = if (tagName.isNullOrEmpty()) {
                AppUtil::class.java.name
            } else {
                tagName
            }

            val description = if (description == null || description.isEmpty()) {
                "Error description is not provided"
            } else {
                description
            }
            try {
                when (priority) {
                    Log.VERBOSE -> Log.v(tagName, description)
                    Log.DEBUG -> Log.d(tagName, description)
                    Log.INFO -> Log.i(tagName, description)
                    Log.ERROR -> Log.e(tagName, description)
                    else -> {}
                }
            } catch (e: Exception) {
                Log.e(tagName, description)
            }
            if (!BuildConfig.DEBUG) {
                FirebaseCrashlytics.getInstance().recordException(Throwable("$tagName : $description"))
            }
        }

        fun launchActivity(context: Context, intent: Intent) {
            context.startActivity(intent)
            (context as? Activity?)?.let {
                it.overridePendingTransition(R.anim.slide_out_left, R.anim. slide_in_right)
            }
        }

        // Pass the content resolver to the function along with the imageUri
        fun decodeBitmapFromUri(contentResolver: ContentResolver, imageUri: Uri): Bitmap? {
            return try {
                val inputStream = contentResolver.openInputStream(imageUri)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                inputStream?.close()
                bitmap
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        fun compressImage(bitmap: Bitmap, quality: Int): ByteArray {
            val outputStream = ByteArrayOutputStream()
            // Compress the bitmap with the desired quality and write it to the output stream
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            // Get the compressed image data as a byte array
            val compressedImageBytes = outputStream.toByteArray()
            // Close the output stream to release resources
            outputStream.close()
            return compressedImageBytes
        }

        fun setupConstraintsBuilder(selectedDate: String): CalendarConstraints {
            val constraintsBuilder = CalendarConstraints.Builder()

            // Parse the selectedDate string into a Calendar instance
            val selectedCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            try {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val date = dateFormat.parse(selectedDate)
                if (date != null) {
                    selectedCalendar.time = date
                }
            } catch (e: Exception) {
                // Handle parsing error or provide a default date
            }

            // Set the start date to 100 years before the selected date
            val startCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            startCalendar.timeInMillis = selectedCalendar.timeInMillis
            startCalendar.add(Calendar.YEAR, -100)

            constraintsBuilder.setStart(startCalendar.timeInMillis)
            constraintsBuilder.setEnd(selectedCalendar.timeInMillis)

            // Open the calendar at the selected date
            constraintsBuilder.setOpenAt(selectedCalendar.timeInMillis)

            return constraintsBuilder.build()
        }

        fun shouldShowAds(mContext: Context): Boolean {
            // Retrieve the SharedPreferences instance
            val preferenceStorage = SharedPreferenceStorage(mContext)
            // Get the current ad count from SharedPreferences
            val adCount = preferenceStorage.adCount
            // Check if it's time to show an ad
            val showAd = adCount % 4 == 0
            // Increment the ad count and save it back to SharedPreferences
            preferenceStorage.adCount = adCount + 1
            return showAd
        }

        fun printKeyHash(activity: Activity) {
            try {
                val info: PackageInfo = activity.packageManager.getPackageInfo(
                    activity.packageName,
                    PackageManager.GET_SIGNATURES
                )
                for (signature in info.signatures) {
                    val md = MessageDigest.getInstance("SHA")
                    md.update(signature.toByteArray())
                    Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
                }
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            }
        }

        fun getDate(timestamp: Long): String {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
            val date = Date(timestamp)
            return dateFormat.format(date)
        }

        fun Context.getJsonFromAssets(fileName: String): String? {
            return try {
                assets.open(fileName).use { inputStream ->
                    inputStream.bufferedReader().use { reader ->
                        reader.readText()
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
        }

        fun dipToPx(context: Context, dipValue: Float): Float {
            val density = context.resources.displayMetrics.density
            return dipValue * density
        }

        fun saveJsonFileToInternalStorage(context: Context, fileName: String, inputStream: InputStream?) {
            try {
                val fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE)
                inputStream?.use { input ->
                    fileOutputStream.use { output ->
                        input.copyTo(output)
                    }
                }
            } catch (e: IOException) {
                Log.e("TAG", "Error saving JSON file to internal storage", e)
            }
        }

         fun readJsonFile(context: Context, fileName: String): String {
            try {
                val fileInputStream = context.openFileInput(fileName)
                val reader = BufferedReader(InputStreamReader(fileInputStream))
                val text = StringBuilder()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    text.append(line)
                }
                reader.close()
                return text.toString()
            } catch (e: IOException) {
                Log.e("TAG", "Error reading JSON file from internal storage", e)
            }
            return ""
        }
    }
}