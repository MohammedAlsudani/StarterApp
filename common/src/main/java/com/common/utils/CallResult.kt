package com.common.utils

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class CallResult<out R> {
    data class Success<out T>(val data: T) : CallResult<T>()
    data class Error(val exception: Exception, val errorMessageResource : Int =0 , val errorCode :Int =0, val error_key:String="", val status:String="") : CallResult<Nothing>()
}

val CallResult<*>.succeeded
    get() = this is CallResult.Success && data != null
