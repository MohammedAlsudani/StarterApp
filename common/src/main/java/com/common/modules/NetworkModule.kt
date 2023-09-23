package com.common.modules

import android.app.Application
import com.common.BuildConfig
import com.common.network.AuthorisationInterceptor
import com.common.network.NetworkLiveData
import com.common.network.NetworkResponseAdapterFactory
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import okhttp3.Cache
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Module that provides the Networking dependencies
 *
 */
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttp(authenticationInterceptor: AuthorisationInterceptor) = OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) {
            addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }
        addInterceptor(authenticationInterceptor)
    }.build()

    @Singleton
    @Provides
    fun provideRetrofitForGoogleDrive(okHttpClient: OkHttpClient, callAdapterFactory: NetworkResponseAdapterFactory)
    = Retrofit.Builder().apply {
        baseUrl("https://drive.google.com/")
        client(okHttpClient)
        addConverterFactory(GsonConverterFactory.create())
        addCallAdapterFactory(callAdapterFactory)
    }.build()

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()

    @Provides
    @Singleton
    fun provideHttpCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    @Provides
    fun provideNetworkLiveData(application: Application): NetworkLiveData {
        return NetworkLiveData(application)
    }
}
