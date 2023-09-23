package com.common.network

/**
 * Generic class which encapsulates both data and its state, and is used to
 * expose network status to the domain/business logic and presentation layers.
 *
 * See https://github.com/android/architecture-components-samples/blob
 * /main/GithubBrowserSample/app/src/main/java/com/android/example/github/vo/Resource.kt
 *
 */
sealed class Resource<out T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T : Any>(data: T) : Resource<T>(data)
    class Error(message: String) : Resource<Nothing>(message = message)
}
