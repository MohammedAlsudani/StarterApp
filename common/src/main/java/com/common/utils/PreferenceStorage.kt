package com.common.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.WorkerThread
import androidx.core.content.edit
import com.common.database.entities.User
import com.google.gson.Gson
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


/**
 * Storage for app and user preferences.
 */
interface PreferenceStorage {
    var userInfo: String
    var userId: String
    var localLanguage: String
    var isLocalLanguageSelected: Boolean
    var tokens: Long
    var pendingPoints: Long
    var isPending: Boolean
    var adCount : Int
    val appOpenCount: Int
    val feedbackDontAsk: Boolean
    val isUserStartedTheApp: Boolean

}

/**
 * [PreferenceStorage] impl backed by [android.content.SharedPreferences].
 */
@Singleton
class SharedPreferenceStorage @Inject constructor(private val context: Context) : PreferenceStorage {

    private val prefs: Lazy<SharedPreferences> = lazy {
        // Lazy to prevent IO access to main thread.
        context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).apply {
            registerOnSharedPreferenceChangeListener(changeListener)
        }
    }



    override var localLanguage: String by StringPreference(prefs, PREF_LOCAL_LANGUAGE,"en")
    override var isLocalLanguageSelected: Boolean by BooleanPreference(prefs, PREF_IS_LOCAL_LANGUAGE_SELECTED,false)
    override var userId: String by StringPreference(prefs, PREF_USER_ID)
    override var userInfo: String by StringPreference(prefs, PREF_USER_INFO)
    override var tokens: Long by LongPreference(prefs, PREF_TOKENS)
    override var pendingPoints: Long by LongPreference(prefs, PREF_PENDING_POINTS)
    override var isPending: Boolean by BooleanPreference(prefs, PREF_IS_PENDING,false)
    override var adCount: Int by IntegerPreference(prefs, PREF_AD_COUNT_KEY,0)
    override var appOpenCount: Int by IntegerPreference(prefs, PREF_OPEN_APP_COUNT,0)
    override var feedbackDontAsk: Boolean by BooleanPreference(prefs, PREF_FEEDBACK_DONT_ASK,false)
    override var isUserStartedTheApp: Boolean by BooleanPreference(prefs, PREF_IS_USER_STARTED_THEAPP,false)


    private val changeListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
    }

    companion object {
        const val PREFS_NAME = "SHARE_WIN_PREFERENCES"
        const val PREF_LOCAL_LANGUAGE = "pref_local_language"
        const val PREF_IS_LOCAL_LANGUAGE_SELECTED = "pref_is_local_language_selected"
        const val PREF_USER_ID = "pref_user_id"
        const val PREF_TOKENS = "pref_tokens"
        const val PREF_PENDING_POINTS = "pref_pending_points"
        const val PREF_IS_PENDING = "pref_is_pending"
        const val PREF_USER_INFO = "pref_user_info"
        const val PREF_AD_COUNT_KEY = "pref_ad_count_key"
        const val PREF_FEEDBACK_DONT_ASK = "pref_feedback_dont_ask"
        const val PREF_OPEN_APP_COUNT = "pref_open_app_count"
        const val PREF_IS_USER_STARTED_THEAPP = "pref_is_user_started_theapp"
    }

    fun clear() {
        prefs.value.edit().clear().apply()
    }

    fun getUserInfo(): User? {
        return if (userInfo.isNotEmpty()){
            Gson().fromJson(userInfo, User::class.java)
        } else {
            null
        }
    }

    fun saveUserData(user: User?) {
        userInfo = Gson().toJson(user)
    }
}

class BooleanPreference(
    private val preferences: Lazy<SharedPreferences>,
    private val name: String,
    private val defaultValue: Boolean
) : ReadWriteProperty<Any, Boolean> {

    @WorkerThread
    override fun getValue(thisRef: Any, property: KProperty<*>): Boolean {
        return preferences.value.getBoolean(name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) {
        preferences.value.edit { putBoolean(name, value) }
    }
}

class StringPreference(
    private val preferences: Lazy<SharedPreferences>,
    private val name: String,
    private val defaultValue: String = ""
) : ReadWriteProperty<Any, String?> {

    @WorkerThread
    override fun getValue(thisRef: Any, property: KProperty<*>): String {
        return preferences.value.getString(name, defaultValue).toString()
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: String?) {
        preferences.value.edit { putString(name, value) }
    }
}
class IntegerPreference(
    private val preferences: Lazy<SharedPreferences>,
    private val name: String,
    private val defaultValue: Int = 0
) : ReadWriteProperty<Any, Int> {

    @WorkerThread
    override fun getValue(thisRef: Any, property: KProperty<*>): Int {
        return preferences.value.getInt(name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Int) {
        preferences.value.edit { putInt(name, value) }
    }
}

class LongPreference(
    private val preferences: Lazy<SharedPreferences>,
    private val name: String,
    private val defaultValue: Long = 0L
) : ReadWriteProperty<Any, Long> {

    @WorkerThread
    override fun getValue(thisRef: Any, property: KProperty<*>): Long {
        return preferences.value.getLong(name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Long) {
        preferences.value.edit { putLong(name, value) }
    }
}

class SecureStringPreference(
    private val preferences: Lazy<SharedPreferences>,
    private val name: String,
    private val defaultValue: String = ""
) : ReadWriteProperty<Any, String?> {

    private val cryptoManager: CryptoManager by lazy {
        CryptoManager()
    }

    @WorkerThread
    override fun getValue(thisRef: Any, property: KProperty<*>): String {
        val encryptedValue = preferences.value.getString(name, defaultValue)
        if (encryptedValue != null) {
            return cryptoManager.decrypt(encryptedValue)
        }
        return defaultValue
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: String?) {
        preferences.value.edit {
            val encryptedValue = value?.let { cryptoManager.encrypt(it) }
            putString(name, encryptedValue)
        }
    }
}