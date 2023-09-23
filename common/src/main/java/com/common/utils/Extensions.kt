package com.common.utils


import android.content.Intent
import android.content.res.Resources.getSystem
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import com.google.android.material.appbar.AppBarLayout

val Float.dp: Float
    get() = (this * getSystem().displayMetrics.density + 0.5f)



inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
    Build.VERSION.SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
}

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    Build.VERSION.SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}


