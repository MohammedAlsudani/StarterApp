package com.common.network

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * An [Interceptor] that adds the Authorisation header with a
 * server-supplied authorisation token to all network requests.
 *
 */
class AuthorisationInterceptor @Inject constructor(private val sessionManager: SessionManager) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
            .addHeader("Content-Type", sessionManager.contentType)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}
