package com.common.utils

import com.common.network.ErrResponse
import com.common.network.NetworkResponse


/**
 * common Error Api Handler.
 */
class CommonErrorHandler {

    companion object {
        /**
         *  on Server error, it can be throwing in case the server request failed
         */
        fun onServerError(response: NetworkResponse.ServerError<ErrResponse>): CallResult.Error {
            return CallResult.Error(exception = Exception(response.body?.message),
                status = response.body?.status.toString(),
                errorCode = response.code)
        }

        /**
         *  on network error, it can be throwing in case the network faild
         */
        fun onNetworkError(response: NetworkResponse.NetworkError): CallResult.Error {
            return CallResult.Error(Exception(response.error.message))
        }

        /**
         *  on local error, it can be throwing in case the API ENDPOINT failed to connect or
         *  the http not initiates.
         */
        fun onLocalError(response: NetworkResponse<Any, ErrResponse>): CallResult.Error {
            return CallResult.Error(Exception(response.toString()))
        }
    }
}