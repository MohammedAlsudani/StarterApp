package com.common.network

import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response

/**
 * An implementation of the [Call] interface from Retrofit
 * used to send a request to the remote webserver, retrieve a response, and
 * parameterize said response within the [NetworkResponse] class in terms of the appropriate sub-type.
 *
 * @param callDelegate Retrofit's [Call] instance injected in [NetworkResponseAdapter.adapt]
 * to whom network operations i.e. sending requests/getting responses will be delegated.
 *
 * @param errorBodyConverter A Converter from Retrofit injected in
 * [NetworkResponseAdapter.adapt] to desererialise Retrofit's [Response] error body into the
 * supplied error class type.
 *
 */
internal class NetworkResponseCall<S : Any, E : Any>(
    private val backingCall: Call<S>,
    private val errorConverter: Converter<ResponseBody, E>
) : Call<NetworkResponse<S, E>> {

    override fun enqueue(callback: Callback<NetworkResponse<S, E>>) = synchronized(this) {
        backingCall.enqueue(object : Callback<S> {
            override fun onResponse(call: Call<S>, response: Response<S>) {
                val body = response.body()
                val headers = response.headers()
                val code = response.code()
                val errorBody = response.errorBody()
                if (response.isSuccessful) {
                    if (body != null) {
                        callback.onResponse(this@NetworkResponseCall, Response.success(
                            NetworkResponse.Success(body, headers)))
                    } else {
                        // Response is successful but the body is null, so there's probably a server error here
                        callback.onResponse(this@NetworkResponseCall, Response.success(
                            NetworkResponse.ServerError(null, code, headers)))
                    }
                } else {
                    val convertedErrorBody = try { errorConverter.convert(errorBody) } catch (ex: Exception) { null }
                    var errorMessage = "Something went wrong, try again later"
                    if (convertedErrorBody is ErrResponse){
                        convertedErrorBody.errors.firstOrNull()?.desc?.let {
                            errorMessage = it
                        }
                    }
                    callback.onResponse(this@NetworkResponseCall, Response.success(NetworkResponse.ServerError(convertedErrorBody,code,headers,errorMessage)))
                }
            }

            /**
             * Handles IOException or any other HTTPException
             */
            override fun onFailure(call: Call<S>, throwable: Throwable) {
                val networkResponse = throwable.extractNetworkResponse<S, E>(errorConverter)
                callback.onResponse(this@NetworkResponseCall, Response.success(networkResponse))
            }
        })
    }

    override fun isExecuted(): Boolean = synchronized(this) {
        backingCall.isExecuted
    }

    override fun clone(): Call<NetworkResponse<S, E>> = NetworkResponseCall(backingCall.clone(), errorConverter)

    override fun isCanceled(): Boolean = synchronized(this) {
        backingCall.isCanceled
    }

    override fun cancel() = synchronized(this) {
        backingCall.cancel()
    }

    override fun execute(): Response<NetworkResponse<S, E>> {
        throw UnsupportedOperationException("Network Response call does not support synchronous execution")
    }

    override fun request(): Request = backingCall.request()
    override fun timeout(): Timeout = backingCall.timeout()
}
