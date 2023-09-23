package com.common.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Error response data type for the requests*
 */
data class ErrResponse(
    @SerializedName("status")
    @Expose
    var status: String = "",
    @SerializedName("errors")
    @Expose
    var errors: List<Error> = ArrayList(),
    @SerializedName("message")
    @Expose
    val message: String = "",
    @SerializedName("user")
    @Expose
    val user: Boolean = false
) {
    override fun toString(): String {
        return "ErrResponse(status='$status', errors=$errors, message='$message', user=$user)"
    }
}

data class Error(
    @SerializedName("desc")
    @Expose
    var desc: String = "",
    @SerializedName("code")
    @Expose
    var code: String = ""
) {
    override fun toString(): String {
        return "Error(desc='$desc', code='$code')"
    }
}