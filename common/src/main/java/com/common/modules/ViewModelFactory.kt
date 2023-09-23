package com.common.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton


@Singleton
class ViewModelFactory @Inject constructor(private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModelProvider = creators[modelClass]
            ?: creators.asIterable().firstOrNull { entry ->
                modelClass.isAssignableFrom(entry.key)
            }?.value
            ?: throw IllegalArgumentException("$UNKNOWN_MODEL_EXCEPTION $modelClass")

        try {
            @Suppress("UNCHECKED_CAST")
            return viewModelProvider.get() as T
        } catch (e: Exception) {
            throw RuntimeException(EXCEPTION_MESSAGE, e)
        }
    }

    companion object {
        private const val EXCEPTION_MESSAGE = "Failed to create ViewModel"
        private const val UNKNOWN_MODEL_EXCEPTION = "Unknown ViewModel class"
    }
}
