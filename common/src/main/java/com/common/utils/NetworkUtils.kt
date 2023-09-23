package com.common.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

object NetworkUtils {

    fun isOnline(connectivityManager: ConnectivityManager?): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val n = connectivityManager?.activeNetwork
            if (n != null) {
                val nc = connectivityManager.getNetworkCapabilities(n)
                // It will check for both wifi and cellular network
                return nc?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true ||
                        nc?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true
            }
            return false
        } else {
            val netInfo = connectivityManager?.activeNetworkInfo
            return netInfo != null && netInfo.isConnectedOrConnecting
        }
    }
}
