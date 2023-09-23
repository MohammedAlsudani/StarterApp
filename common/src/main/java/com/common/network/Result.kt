package com.common.network

import com.common.R


/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception,
                     val status : String="",
                     val errorMessageResource : Int = R.string.common_server_error,
                     val errorCode :Int =0,
                     val error_key : String="") : Result<Nothing>()
    data class Loading(val isLoading: Boolean) : Result<Nothing>()
}

/**
 * `true` if [Result] is of type [Success] & holds non-null [Success.data].
 */
val Result<*>.succeeded
    get() = this is Result.Success && data != null
