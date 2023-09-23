package com.common.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Converter
import java.lang.reflect.Type

/**
 * This handles networking for service interface methods
 * that return [NetworkResponse] as their return type.
 *
 * It does this by adapting a [Call] with the response type, T into a Call<ApiResponse<T, E>> type
 * using an [NetworkResponseCall] instance.
 *
 */
class NetworkResponseAdapter<T : Any, E : Any>(
    private val successBodyType: Type,
    private val errorBodyConverter: Converter<ResponseBody, E>
) : CallAdapter<T, Call<NetworkResponse<T, E>>> {

    /**
     * Returns a type that this adapter ultimately
     * uses as the type of an instance to which the HTTP response body
     * will be converted. This type is also used to prepare the [Call] passed into [adapt].
     */
    override fun responseType(): Type = successBodyType

    /**
     * Returns an [NetworkResponseCall] instance that delegates network ops to the passed-in Call<T>
     * instance and parameterizes the response type T, within [NetworkResponse].
     */
    override fun adapt(call: Call<T>): Call<NetworkResponse<T, E>> {
        return NetworkResponseCall(call, errorBodyConverter)
    }
}
