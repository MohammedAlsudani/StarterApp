package com.common.network

import com.common.utils.PreferenceStorage
import javax.inject.Inject
import javax.inject.Singleton

/**
 * This is responsible for storing and managing the state of the
 * signed-in user's session.
 *
 */
@Singleton
class SessionManager @Inject constructor(private val preference: PreferenceStorage) {

    val contentType: String = "application/json; charset=utf-8"

}
