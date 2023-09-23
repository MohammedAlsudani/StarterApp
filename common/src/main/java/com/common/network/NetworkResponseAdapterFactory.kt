package com.common.network

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import javax.inject.Inject

/**
 * This creates [NetworkResponseAdapter] instances to handle networking
 * for service interface functions that return [NetworkResponse].
 *
 */
class NetworkResponseAdapterFactory @Inject constructor() : CallAdapter.Factory() {

    /**
     * Returns an [NetworkResponseAdapter] instance if the called service interface function
     * returns and/or can handle [NetworkResponse], or null if it does not as other [returnType]s
     * cannot be handled by this factory. One may delegate to a factory that can create
     * an adapter to handle such.
     */
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        // suspend functions internally wraps the return type in Call
        if (getRawType(returnType) != Call::class.java) {
            return null
        }

        check(returnType is ParameterizedType) {
            "return type must be parameterized as Call<ApiResponse<<Foo>>" +
                " or Call<ApiResponse<out Foo>>"
        }

        // get the parameter at index 0 of the returnType
        val responseType = getParameterUpperBound(0, returnType)

        if (getRawType(responseType) != NetworkResponse::class.java) {
            return null
        }

        check(responseType is ParameterizedType) {
            "response type must be parameterized as ApiResponse<<Foo>" +
                " or ApiResponse<out Foo>"
        }

        val successBodyType = getParameterUpperBound(0, responseType)
        val errorBodyType = getParameterUpperBound(1, responseType)

        val errorBodyConverter =
            retrofit.nextResponseBodyConverter<Any>(null, errorBodyType, annotations)

        return NetworkResponseAdapter<Any, Any>(successBodyType, errorBodyConverter)
    }
}
